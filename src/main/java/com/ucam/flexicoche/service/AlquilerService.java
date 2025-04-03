package com.ucam.flexicoche.service;

import java.util.List;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.dto.RegistrarAlquilerDTO;

public interface AlquilerService {

	List<AlquilerDTO> findByCorreo(String correo);

	void registrarAlquiler(String correo, RegistrarAlquilerDTO alquiler) throws Exception;
}
