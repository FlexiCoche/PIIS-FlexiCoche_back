package com.ucam.flexicoche.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import com.ucam.flexicoche.model.Alquiler;
import com.ucam.flexicoche.repository.AlquilerRepository;
import com.ucam.flexicoche.service.AlquilerService;

@Service
public class AlquilerServiceImpl implements AlquilerService {

	@Autowired
	private AlquilerRepository alquilerRepository;

	@Autowired
	private FlexiCocheMapper mapper;

	public List<AlquilerDTO> findByCorreo(String correo) {
		List<Alquiler> alquileres = alquilerRepository.findByUsuario(correo);

		return mapper.toAlquileresDto(alquileres);
	}

}
