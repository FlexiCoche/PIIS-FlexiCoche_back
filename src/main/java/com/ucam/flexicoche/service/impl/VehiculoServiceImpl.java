package com.ucam.flexicoche.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.dto.LocalizacionDTO;
import com.ucam.flexicoche.dto.VehiculoDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import com.ucam.flexicoche.model.*;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.repository.VehiculoRepository;
import com.ucam.flexicoche.service.VehiculoService;
import com.ucam.flexicoche.specification.VehiculoSpecification;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private FlexiCocheMapper flexiCocheMapper;

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

	// 🖊️ Actualizar color y precio
	/*@Override
	public Vehiculo updateVehiculo(String matricula, String color, Float precio) {
		Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);
		if (vehiculo == null) {
			throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
		}

		vehiculo.setColor(color);
		vehiculo.setPrecioDia(precio);
		return vehiculoRepository.save(vehiculo);
	}*/
	
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

	    // Si es coche, moto, etc. puedes mapear sus campos específicos también:
	    if (existente instanceof Coche && vehiculoActualizado instanceof Coche) {
	        Coche cocheExistente = (Coche) existente;
	        Coche cocheActualizado = (Coche) vehiculoActualizado;
	        cocheExistente.setCarroceria(cocheActualizado.getCarroceria());
	        cocheExistente.setPuertas(cocheActualizado.getPuertas());
	        cocheExistente.setPotencia(cocheActualizado.getPotencia());
	    }

	    // Puedes añadir más bloques similares para Moto, Furgoneta, etc.

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
	public void deleteVehiculo(String correo, String matricula) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (!usuario.getRoles().contains("ADMIN")) {
				throw new RuntimeException("El usuario no tiene permisos para eliminar vehículos");
			}
		}
		
		vehiculoRepository.deleteByMatricula(matricula);
	}

	// 🖼️ Actualizar imagen desde URL
	@Override
	public Vehiculo updateVehiculoImagenDesdeURL(String correo, String matricula, String imageUrl) {
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

		ImagenVehiculo imagenVehiculo = vehiculo.getImagen();
		if (imagenVehiculo == null) {
			imagenVehiculo = new ImagenVehiculo();
			imagenVehiculo.setVehiculo(vehiculo);
			vehiculo.setImagen(imagenVehiculo);
		}

		imagenVehiculo.setImagen(imageUrl);
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

	// ⚙️ Actualizar solo la potencia del coche
	@Override
	public Coche updateVehiculoCochePotencia(String correo, String matricula, int potencia) {
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
		coche.setPotencia(potencia);
		return vehiculoRepository.save(coche);
	}
}
