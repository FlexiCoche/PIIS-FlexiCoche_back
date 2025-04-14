package com.ucam.flexicoche.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public abstract class VehiculoDTO {
	private Long id;
	private String matricula;
	private String combustible;
	private String color;
	private Float precioDia;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date anioMatricula;
	private int disponibilidad;
	private String marca;
	private String modelo;
	private Long nPlazas;
	private String transmision;
	private String localizacion;
	private String imagenUrl;
}
