package com.ucam.flexicoche.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ucam.flexicoche.dto.LocalizacionDTO;
import com.ucam.flexicoche.dto.VehiculoDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Camion;
import com.ucam.flexicoche.model.Furgoneta;
import com.ucam.flexicoche.model.ImagenVehiculo;
import com.ucam.flexicoche.model.Moto;
import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.service.GoogleDriveService;
import com.ucam.flexicoche.service.impl.VehiculoServiceImpl;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

	@Autowired
	private VehiculoServiceImpl vehiculoServiceImpl;
	
	@Autowired
	private GoogleDriveService googleDriveService;

	@Autowired
	private FlexiCocheMapper mapper;

	// Búsqueda con filtros múltiples
	@GetMapping("/buscador")
	public List<VehiculoDTO> buscarVehiculosDTO(
			@RequestParam(required = false) String tipo,
			@RequestParam(required = false) String marca,
			@RequestParam(required = false) String modelo,
			@RequestParam(required = false) String localizacion,
			@RequestParam(required = false) String color,
			@RequestParam(required = false) String combustible,
			@RequestParam(required = false) Long nPlazas,
			@RequestParam(required = false) String transmision,
			@RequestParam(required = false) Long precioMin,
			@RequestParam(required = false) Long precioMax,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
	) {
		List<Vehiculo> vehiculos = vehiculoServiceImpl.buscarVehiculos(tipo, marca, modelo, localizacion, color, combustible, nPlazas, transmision, precioMin, precioMax, fechaInicio, fechaFin);
		return vehiculos.stream()
				.map(v -> {
					VehiculoDTO dto = mapper.toVehiculoDto(v);
					if (dto != null && (dto.getImagenUrl() == null || dto.getImagenUrl().isEmpty())) {
						dto.setImagenUrl("/assets/images/default.png");
					}
					return dto;
				})
				.filter(dto -> dto != null)
				.collect(Collectors.toList());
	}

	// Obtener todos los vehículos
	@GetMapping
	public ResponseEntity<List<Vehiculo>> getVehiculos() {
		return ResponseEntity.ok(vehiculoServiceImpl.getVehiculos());
	}

	// Buscar por matrícula
	@GetMapping("/matricula/{matricula}")
	public ResponseEntity<Vehiculo> getVehiculoMatricula(@PathVariable String matricula) {
		return ResponseEntity.ok(vehiculoServiceImpl.findVehiculoByMatricula(matricula));
	}

	// Obtener localizaciones únicas
	@GetMapping("/localizaciones")
	public ResponseEntity<List<String>> getLocalizaciones() {
		List<String> descripciones = vehiculoServiceImpl.getLocalizaciones();
		return ResponseEntity.ok(descripciones);
	}
	
	@GetMapping("/localizaciones/detallado")
	public ResponseEntity<List<LocalizacionDTO>> getLocalizacionesDetalladas() {
	    List<LocalizacionDTO> localizaciones = vehiculoServiceImpl.getLocalizacionesDetalladas();
	    return ResponseEntity.ok(localizaciones);
	}


	// Añadir vehículo
	/*@PostMapping("/add")
	public ResponseEntity<Vehiculo> setVehiculo(@RequestBody Vehiculo vehiculo) {
		return ResponseEntity.ok(vehiculoServiceImpl.setVehiculo(vehiculo));
	}*/
	
	
	@PostMapping("/add")
	public ResponseEntity<Vehiculo> setVehiculoConImagen(
	        @RequestPart("vehiculo") String vehiculoJson,
	        @RequestPart("imagen") MultipartFile imagen) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        //Vehiculo vehiculo = objectMapper.readValue(vehiculoJson, Vehiculo.class);
	        JsonNode rootNode = objectMapper.readTree(vehiculoJson);

	        // 1. Obtener y eliminar el campo "tipo" del JSON
	        String tipo = rootNode.has("tipo") ? rootNode.get("tipo").asText() : null;
	        if (rootNode instanceof ObjectNode) {
	            ((ObjectNode) rootNode).remove("tipo");
	        }

	        // 2. Deserializar en la clase hija correspondiente
	        Vehiculo vehiculo;
	        if (rootNode.has("localizacion") && rootNode.get("localizacion").isInt()) {
	            int idLocalizacion = rootNode.get("localizacion").asInt();
	            
	            // Sustituir el número por un objeto JSON del tipo que Jackson espera
	            ObjectNode localizacionNode = objectMapper.createObjectNode();
	            localizacionNode.put("localizacion", idLocalizacion);
	            ((ObjectNode) rootNode).set("localizacion", localizacionNode);
	        }
	        
	        switch (tipo) {
	            case "COCHE":
	                vehiculo = objectMapper.treeToValue(rootNode, Coche.class);
	                break;
	            case "MOTO":
	                vehiculo = objectMapper.treeToValue(rootNode, Moto.class);
	                break;
	            case "FURGONETA":
	                vehiculo = objectMapper.treeToValue(rootNode, Furgoneta.class);
	                break;
	            case "CAMION":
	                vehiculo = objectMapper.treeToValue(rootNode, Camion.class);
	                break;
	            default:
	                return ResponseEntity.badRequest().build(); // tipo inválido
	        }
	        
	        Vehiculo vehiculoGuardado = vehiculoServiceImpl.setVehiculo(vehiculo); 
	        
	        String imageUrl = googleDriveService.uploadFile(imagen);

	        ImagenVehiculo imagenVehiculo = new ImagenVehiculo();
	        imagenVehiculo.setImagen(imageUrl);
	        imagenVehiculo.setVehiculo(vehiculoGuardado);
	        imagenVehiculo.setId_vehiculo(vehiculoGuardado.getId());

	        vehiculoGuardado.setImagen(imagenVehiculo);

	        Vehiculo guardadoConImagen = vehiculoServiceImpl.setVehiculo(vehiculoGuardado);

	        return ResponseEntity.ok(guardadoConImagen);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	/*@PostMapping("/add")
	public ResponseEntity<Vehiculo> setVehiculoConImagen(
	        @RequestPart("vehiculo") String vehiculoJson,
	        @RequestPart("imagen") MultipartFile imagen) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Vehiculo vehiculo = objectMapper.readValue(vehiculoJson, Vehiculo.class);
	        System.out.println(vehiculo.getId());
	        String imageUrl = googleDriveService.uploadFile(imagen);
	        ImagenVehiculo imagenVehiculo = new ImagenVehiculo();
	        imagenVehiculo.setImagen(imageUrl);
	        imagenVehiculo.setId_vehiculo(vehiculo.getId());
	        vehiculo.setImagen(imagenVehiculo);

	        Vehiculo guardado = vehiculoServiceImpl.setVehiculo(vehiculo);
	        return ResponseEntity.ok(guardado);

	    } catch (Exception e) {
	        e.printStackTrace();  // LOG del error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}*/

	// Actualizar color y precio
	@PutMapping("/updateVehiculo/{matricula}")
	public ResponseEntity<Vehiculo> updateVehiculo(
			@PathVariable String matricula,
			@RequestParam String color,
			@RequestParam Float precio
	) {
		Vehiculo actualizado = vehiculoServiceImpl.updateVehiculo(matricula, color, precio);
		return ResponseEntity.ok(actualizado);
	}

	// Cambiar disponibilidad
	@PutMapping("/updateDisponibilidad/{matricula}")
	public ResponseEntity<Vehiculo> updateDisponibilidad(
			@PathVariable String matricula,
			@RequestParam int disponibilidad
	) {
		Vehiculo actualizado = vehiculoServiceImpl.updateStateVehiculo(matricula, disponibilidad);
		return ResponseEntity.ok(actualizado);
	}

	// Actualizar imagen
	@PutMapping("/{matricula}/imagen")
	public ResponseEntity<String> actualizarImagen(
			@PathVariable String matricula,
			@RequestParam String imageUrl
	) {
		try {
			vehiculoServiceImpl.updateVehiculoImagenDesdeURL(matricula, imageUrl);
			return ResponseEntity.ok("Imagen actualizada correctamente.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al actualizar la imagen: " + e.getMessage());
		}
	}

	// Actualizar campos específicos de coche
	@PutMapping("/updateVehiculoCoche/{matricula}")
	public ResponseEntity<Coche> updateVehiculoCoche(
			@PathVariable String matricula,
			@RequestParam String carroceria,
			@RequestParam int puertas,
			@RequestParam int potencia
	) {
		Coche actualizado = vehiculoServiceImpl.updateVehiculoCoche(matricula, carroceria, puertas, potencia);
		return ResponseEntity.ok(actualizado);
	}

	// Solo potencia
	@PutMapping("/updatePotencia/{matricula}")
	public ResponseEntity<Coche> updatePotencia(
			@PathVariable String matricula,
			@RequestParam int potencia
	) {
		Coche actualizado = vehiculoServiceImpl.updateVehiculoCochePotencia(matricula, potencia);
		return ResponseEntity.ok(actualizado);
	}

	// Eliminar vehículo
	@DeleteMapping("/{matricula}")
	public void deleteVehiculo(@PathVariable String matricula) {
		vehiculoServiceImpl.deleteVehiculo(matricula);
	}
	
}