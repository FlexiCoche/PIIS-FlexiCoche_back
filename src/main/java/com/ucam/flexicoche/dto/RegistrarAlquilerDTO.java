package com.ucam.flexicoche.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RegistrarAlquilerDTO {
	private Long idVehiculo;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp fechaInicio;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp fechaFin;
}
