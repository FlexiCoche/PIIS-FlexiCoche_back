package com.ucam.flexicoche.service;

import java.util.List;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.dto.EstadoDTO;
import com.ucam.flexicoche.dto.RegistrarAlquilerDTO;

public interface AlquilerService {

	List<AlquilerDTO> findByCorreo(String correo);

	void registrarAlquiler(String correo, RegistrarAlquilerDTO alquiler) throws Exception;
	
	void cancelarAlquiler(String correo, Long idAlquiler) throws Exception;

	void pagarAlquiler(Long id);
	
	void anularAlquiler(Long id);

	void cambiarEstado(String correo, EstadoDTO estado) throws Exception;
	
    void deleteAllByVehiculoId(Long vehiculoId);

}
