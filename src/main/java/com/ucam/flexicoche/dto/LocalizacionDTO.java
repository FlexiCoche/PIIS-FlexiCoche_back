package com.ucam.flexicoche.dto;

import lombok.Data;

@Data
public class LocalizacionDTO {
	private Integer localizacion;
	private String descripcion;
	
	public LocalizacionDTO(Integer localizacion, String descripcion) {
        this.localizacion = localizacion;
        this.descripcion = descripcion;
    }
}
