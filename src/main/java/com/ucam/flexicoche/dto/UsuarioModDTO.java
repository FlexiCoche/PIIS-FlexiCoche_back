package com.ucam.flexicoche.dto;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UsuarioModDTO {
	private String nombre;
	private String apellidos;
	private Long telefono;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaNacimiento;
	private String nDocumento;
}
