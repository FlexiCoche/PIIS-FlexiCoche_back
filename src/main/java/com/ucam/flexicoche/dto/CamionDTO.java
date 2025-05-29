package com.ucam.flexicoche.dto;

import lombok.Data;

@Data
public class CamionDTO extends VehiculoDTO{

	private float pesoMax;
	private float altura;
	private int numRemolques;
	private String tipoCarga;
	private String matriculaRemolque;
}
