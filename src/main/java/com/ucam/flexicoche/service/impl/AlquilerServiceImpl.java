package com.ucam.flexicoche.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.dto.RegistrarAlquilerDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import com.ucam.flexicoche.model.Alquiler;
import com.ucam.flexicoche.model.Estado;
import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.repository.AlquilerRepository;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.repository.VehiculoRepository;
import com.ucam.flexicoche.service.AlquilerService;

@Service
public class AlquilerServiceImpl implements AlquilerService {

	@Autowired
	private AlquilerRepository alquilerRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private FlexiCocheMapper mapper;

	public List<AlquilerDTO> findByCorreo(String correo) {
		List<Alquiler> alquileres = alquilerRepository.findByUsuario(correo);

		return mapper.toAlquileresDto(alquileres);
	}

	@Override
	public void registrarAlquiler(String correo, RegistrarAlquilerDTO alquiler) throws Exception {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		// ¿que el usuario no tenga reservas en las mismas fechas? ¿que el vehículo esté
		// disponible?
		// ¿en qué estado se registra el alquiler? ¿a pagar?

		if (alquiler.getFechaInicio() == null || alquiler.getFechaFin() == null || alquiler.getIdVehiculo() == null) {
			throw new Exception("Faltan datos obligatorios");
		}

		if (usuarioOpt.isPresent()) {
			Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(alquiler.getIdVehiculo());

			if (vehiculoOpt.isEmpty()) {
				throw new Exception("Vehiculo no encontrado");
			}

			if (vehiculoOpt.get().getDisponibilidad() == 0) {
				throw new Exception("Vehiculo no disponible");
			}
			// Validar que el usuario no tenga reservas en las mismas fechas
			List<Alquiler> alquileres = alquilerRepository.findByUsuario(correo);
			if (alquileres.stream()
					.anyMatch(x -> x.getFechaInicio().after(alquiler.getFechaInicio())
							&& x.getFechaInicio().before(alquiler.getFechaFin())
							|| x.getFechaFin().after(alquiler.getFechaInicio())
									&& x.getFechaInicio().before(alquiler.getFechaFin()))) {
				throw new Exception("El usuario ya tiene un alquiler en las fechas introducidas");
			}

			Alquiler alquilerNew = new Alquiler();
			alquilerNew.setVehiculo(vehiculoOpt.get());
			alquilerNew.setEstado(Estado.A_PAGAR);
			alquilerNew.setFechaInicio(alquiler.getFechaInicio());
			alquilerNew.setFechaFin(alquiler.getFechaFin());
			alquilerNew.setUsuario(usuarioOpt.get());

			alquilerRepository.save(alquilerNew);
		} else {
			throw new Exception("Usuario no encontrado");
		}
	}

}
