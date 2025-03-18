package com.ucam.flexicoche.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VehiculoDTO {
	private Long id;
	private String matricula;
	private String combustible;
	private String color;
	private Float precioDia;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date anioMatricula;
	private int disponibilidad;
	private String nombre;
	private Long nPlazas;
	private String transmision;
}
