package com.ucam.flexicoche.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AlquilerDTO {
	private Long id;
	private VehiculoDTO vehiculo;
	private String estado;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private UsuarioDTO2 usuario;
}
