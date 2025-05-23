package com.ucam.flexicoche.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ucam.flexicoche.dto.LocalizacionDTO;
import com.ucam.flexicoche.dto.VehiculoDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import com.ucam.flexicoche.model.*;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.repository.VehiculoRepository;
import com.ucam.flexicoche.service.CloudinaryService;
import com.ucam.flexicoche.service.VehiculoService;
import com.ucam.flexicoche.specification.VehiculoSpecification;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AlquilerServiceImpl alquilerServiceImpl;
	
	@Autowired
	private FlexiCocheMapper flexiCocheMapper;

	@Autowired
	private CloudinaryService googleDriveService;

	// 🔍 Búsqueda múltiple con Specification
	public List<Vehiculo> buscarVehiculos(String tipo, String marca, String modelo, String localizacion, String color,
										  String combustible, Long nPlazas, String transmision,
										  Long precioMin, Long precioMax,
										  LocalDate fechaInicio, LocalDate fechaFin) {
		return vehiculoRepository.findAll(VehiculoSpecification.filtrar(
				tipo, marca, modelo, localizacion, color, combustible,
				nPlazas, transmision, precioMin, precioMax, fechaInicio, fechaFin
		));
	}

	// 🔍 Versión DTO del buscador
	public List<VehiculoDTO> buscarVehiculosDTO(String tipo, String marca, String modelo, String localizacion, String color,
												String combustible, Long nPlazas, String transmision,
												Long precioMin, Long precioMax,
												LocalDate fechaInicio, LocalDate fechaFin) {
		List<Vehiculo> vehiculos = buscarVehiculos(tipo, marca, modelo, localizacion, color,
				combustible, nPlazas, transmision, precioMin, precioMax, fechaInicio, fechaFin);
		return vehiculos.stream()
				.map(flexiCocheMapper::toVehiculoDto)
				.collect(Collectors.toList());
	}

	// 📍 Localizaciones únicas
	public List<String> getLocalizaciones() {
		return vehiculoRepository.findAllLocalizacionesUnicas();
	}

	// 📍 Localizaciones únicas y detalladas
	public List<LocalizacionDTO> getLocalizacionesDetalladas() {
		return vehiculoRepository.findAllLocalizacionesUnicasDetalladas();
	}
		
	// 📌 Obtener todos los vehículos
	@Override
	public List<Vehiculo> getVehiculos() {
		return vehiculoRepository.findAll();
	}

	// 🔍 Buscar por marca
	@Override
	public Vehiculo findVehiculoByMarca(String marca) {
		return vehiculoRepository.findByMarca(marca);
	}

	// 🔍 Buscar por matrícula
	@Override
	public Vehiculo findVehiculoByMatricula(String matricula) {
		return vehiculoRepository.findByMatricula(matricula);
	}

	// ➕ Crear nuevo vehículo
	@Override
	public Vehiculo setVehiculo(String correo, Vehiculo vehiculo) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para crear vehículos");
			}
		}
		return vehiculoRepository.save(vehiculo);
	}
	
	@Override
	public Vehiculo updateVehiculo(String correo, String matricula, Vehiculo vehiculoActualizado) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para modificar vehículos");
			}
		}
		
	    Vehiculo existente = vehiculoRepository.findByMatricula(matricula);
	    if (existente == null) {
	        throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
	    }

	    // Campos comunes
	    existente.setColor(vehiculoActualizado.getColor());
	    existente.setMarca(vehiculoActualizado.getMarca());
	    existente.setModelo(vehiculoActualizado.getModelo());
	    existente.setTransmision(vehiculoActualizado.getTransmision());
	    existente.setCombustible(vehiculoActualizado.getCombustible());
	    existente.setNPlazas(vehiculoActualizado.getNPlazas());
	    existente.setPrecioDia(vehiculoActualizado.getPrecioDia());
	    existente.setLocalizacion(vehiculoActualizado.getLocalizacion());
	    existente.setDisponibilidad(vehiculoActualizado.getDisponibilidad());

	    // Campos particulares
	    if (existente instanceof Coche && vehiculoActualizado instanceof Coche) {
	        Coche cocheExistente = (Coche) existente;
	        Coche cocheActualizado = (Coche) vehiculoActualizado;
	        cocheExistente.setCarroceria(cocheActualizado.getCarroceria());
	        cocheExistente.setPuertas(cocheActualizado.getPuertas());
	        cocheExistente.setPotencia(cocheActualizado.getPotencia());
	    } else if (existente instanceof Moto && vehiculoActualizado instanceof Moto) {
	        Moto existenteMoto = (Moto) existente;
	        Moto actualizadoMoto = (Moto) vehiculoActualizado;
	        existenteMoto.setCilindrada(actualizadoMoto.getCilindrada());
	        existenteMoto.setBaul(actualizadoMoto.getBaul());
	    } else if (existente instanceof Furgoneta && vehiculoActualizado instanceof Furgoneta) {
	        Furgoneta existenteFurgo = (Furgoneta) existente;
	        Furgoneta actualizadoFurgo = (Furgoneta) vehiculoActualizado;
	        existenteFurgo.setVolumen(actualizadoFurgo.getVolumen());
	        existenteFurgo.setLongitud(actualizadoFurgo.getLongitud());
	        existenteFurgo.setPesoMax(actualizadoFurgo.getPesoMax());
	    } else if (existente instanceof Camion && vehiculoActualizado instanceof Camion) {
	        Camion existenteCamion = (Camion) existente;
	        Camion actualizadoCamion = (Camion) vehiculoActualizado;
	        existenteCamion.setAltura(actualizadoCamion.getAltura());
	        existenteCamion.setNumRemolques(actualizadoCamion.getNumRemolques());
	        existenteCamion.setTipoCarga(actualizadoCamion.getTipoCarga());
	        existenteCamion.setMatriculaRemolque(actualizadoCamion.getMatriculaRemolque());
	        existenteCamion.setPesoMax(actualizadoCamion.getPesoMax());
	    }


	    return vehiculoRepository.save(existente);
	}


	// 🟢 Cambiar disponibilidad
	@Override
	public Vehiculo updateStateVehiculo(String correo, String matricula, int disponibilidad) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para modificar el estado de vehículos");
			}
		}
		
		Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
		if (vehiculo == null) {
			throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
		}

		vehiculo.setDisponibilidad(disponibilidad);
		return vehiculoRepository.save(vehiculo);
	}

	// 🗑️ Eliminar vehículo
	@Override
	@Transactional
	public void deleteVehiculo(String correo, String matricula) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para eliminar vehículos");
			}
		}
		Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
		
		alquilerServiceImpl.deleteAllByVehiculoId(vehiculo.getId());
		vehiculoRepository.deleteByMatricula(matricula);
	}

	
	@Override
	public Vehiculo updateVehiculoImagenDesdeURL(String correo, String matricula, MultipartFile imagen) throws Exception {
	    // Validar que el usuario es ADMIN
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para modificar el estado de vehículos");
			}
		}

	    // Buscar vehículo
	    Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
	    if (vehiculo == null) {
	        throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
	    }

	    // Subir imagen a Google Drive
	    String imageUrl = googleDriveService.uploadImage(imagen, "vehiculos");

	    // Obtener o crear entidad ImagenVehiculo
	    ImagenVehiculo imagenVehiculo = vehiculo.getImagen();
	    if (imagenVehiculo == null) {
	        imagenVehiculo = new ImagenVehiculo();
	        imagenVehiculo.setImagen(imageUrl);
	        imagenVehiculo.setVehiculo(vehiculo);
	        imagenVehiculo.setId_vehiculo(vehiculo.getId());
	        vehiculo.setImagen(imagenVehiculo);
	    } else if (imagenVehiculo.getId_vehiculo() == null) {
	        imagenVehiculo.setId_vehiculo(vehiculo.getId()); // También cubre el caso si la imagen no tiene ID
	    }

	    return vehiculoRepository.save(vehiculo);
	}
	

	// ⚙️ Actualizar campos de coche
	@Override
	public Coche updateVehiculoCoche(String correo, String matricula, String carroceria, int puertas, int potencia) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para modificar vehículos");
			}
		}
		
		Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
		if (vehiculo == null) {
			throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
		}

		if (!(vehiculo instanceof Coche)) {
			throw new RuntimeException("El vehículo con matrícula " + matricula + " no es un coche.");
		}

		Coche coche = (Coche) vehiculo;
		coche.setCarroceria(carroceria);
		coche.setPuertas(puertas);
		coche.setPotencia(potencia);
		return vehiculoRepository.save(coche);
	}


	// ⚙️ Actualizar campos de moto
	@Override
	public Moto updateVehiculoMoto(String correo, String matricula, int cilindrada, int baul) {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        if (!usuario.getRoles().contains("ADMIN")) {
	            throw new RuntimeException("El usuario no tiene permisos para modificar vehículos");
	        }
	    }

	    Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
	    if (vehiculo == null) {
	        throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
	    }

	    if (!(vehiculo instanceof Moto)) {
	        throw new RuntimeException("El vehículo con matrícula " + matricula + " no es una moto.");
	    }

	    Moto moto = (Moto) vehiculo;
	    moto.setCilindrada(cilindrada);
	    moto.setBaul(baul);
	    return vehiculoRepository.save(moto);
	}

	// ⚙️ Actualizar campos de furgoneta

	@Override
	public Furgoneta updateVehiculoFurgoneta(String correo, String matricula, float volumen, float longitud, float pesoMax) {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        if (!usuario.getRoles().contains("ADMIN")) {
	            throw new RuntimeException("El usuario no tiene permisos para modificar vehículos");
	        }
	    }

	    Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
	    if (vehiculo == null) {
	        throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
	    }

	    if (!(vehiculo instanceof Furgoneta)) {
	        throw new RuntimeException("El vehículo con matrícula " + matricula + " no es una furgoneta.");
	    }

	    Furgoneta furgo = (Furgoneta) vehiculo;
	    furgo.setVolumen(volumen);
	    furgo.setLongitud(longitud);
	    furgo.setPesoMax(pesoMax);
	    return vehiculoRepository.save(furgo);
	}

	// ⚙️ Actualizar campos de camion
	@Override
	public Camion updateVehiculoCamion(String correo, String matricula, float altura, int numRemolques, String tipoCarga, String matriculaRemolque, float pesoMax) {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        if (!usuario.getRoles().contains("ADMIN")) {
	            throw new RuntimeException("El usuario no tiene permisos para modificar vehículos");
	        }
	    }

	    Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
	    if (vehiculo == null) {
	        throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
	    }

	    if (!(vehiculo instanceof Camion)) {
	        throw new RuntimeException("El vehículo con matrícula " + matricula + " no es un camión.");
	    }

	    Camion camion = (Camion) vehiculo;
	    camion.setAltura(altura);
	    camion.setNumRemolques(numRemolques);
	    camion.setTipoCarga(tipoCarga);
	    camion.setMatriculaRemolque(matriculaRemolque);
	    camion.setPesoMax(pesoMax);
	    return vehiculoRepository.save(camion);
	}


}
