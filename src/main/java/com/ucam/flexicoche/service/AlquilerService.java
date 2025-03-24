package com.ucam.flexicoche.service;

import java.util.List;

import com.ucam.flexicoche.dto.AlquilerDTO;

public interface AlquilerService {

	List<AlquilerDTO> findByCorreo(String correo);
}
