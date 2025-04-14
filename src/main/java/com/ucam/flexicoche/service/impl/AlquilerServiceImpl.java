package com.ucam.flexicoche.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.ucam.flexicoche.model.*;
import com.ucam.flexicoche.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.dto.RegistrarAlquilerDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import com.ucam.flexicoche.repository.AlquilerRepository;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.repository.VehiculoRepository;
import com.ucam.flexicoche.service.AlquilerService;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private FacturaRepository facturaRepository;


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

    @Transactional
    public void pagarAlquiler(Long id) {
        Optional<Alquiler> optional = alquilerRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el alquiler con ID: " + id);
        }

        Alquiler alquiler = optional.get();

        if (!alquiler.getEstado().equals(Estado.A_PAGAR)) {
            throw new IllegalStateException("Este alquiler no puede ser pagado en su estado actual: " + alquiler.getEstado());
        }

        try {

            // Verificar si ya existe una factura
            Optional<Factura> facturaExistente = facturaRepository.findByAlquilerId(id);
            if (facturaExistente.isPresent()) {
                throw new IllegalStateException("Este alquiler ya tiene una factura registrada.");
            }

            alquiler.setEstado(Estado.PROCESANDO);
            alquilerRepository.save(alquiler);

            long dias = ChronoUnit.DAYS.between(alquiler.getFechaInicio().toLocalDateTime(), alquiler.getFechaFin().toLocalDateTime());
            double importe = dias * alquiler.getVehiculo().getPrecioDia() + dias * 4.70;

            // Crear factura
            Factura factura = new Factura();
            factura.setAlquiler(alquiler);
            factura.setImporte(importe);

            facturaRepository.save(factura);
        } catch (Exception e) {
            System.out.println("Error al guardar el alquiler: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    @Transactional
    public void anularAlquiler(Long id) {
        Optional<Alquiler> optional = alquilerRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el alquiler con ID: " + id);
        }

        alquilerRepository.deleteById(id);
    }


	@Override
	public void cancelarAlquiler(String correo, Long idAlquiler) throws Exception {
		Optional<Alquiler> alquilerOpt = alquilerRepository.findById(idAlquiler);

		if (!alquilerOpt.isPresent()) {
			throw new Exception("Alquiler no encontrado");
		}
		Alquiler alquiler = alquilerOpt.get();

		// Solo se podrán cancelar alquileres si está en a pagar o procesando
		if (!alquiler.getEstado().equals(Estado.A_PAGAR) || !alquiler.getEstado().equals(Estado.PROCESANDO)) {
			throw new Exception("El alquiler se encuentra en un estado que no permite cancelar");
		}

		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

		if (usuarioOpt.isPresent()) {
			// Solo se podrán borrar alquileres si son del usuario logado o si el usuario es
			// administrador
			if (alquiler.getUsuario().getCorreo().equals(correo) || usuarioOpt.get().getRoles().contains("ADMIN")) {
				alquiler.setEstado(Estado.DENEGADO);
				alquilerRepository.save(alquiler);
			} else {
				throw new Exception("El usuario no tiene permisos para borrar el alquiler seleccionado");
			}
		} else {
			throw new Exception("Usuario no encontrado");
		}
	}

}
