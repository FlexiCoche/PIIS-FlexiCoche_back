package com.ucam.flexicoche.dto;

import lombok.Data;

@Data
public class CocheDTO extends VehiculoDTO {
	private int puertas;

	private String carroceria;

	private int potencia;
}
