package com.ucam.flexicoche.dto;

import java.sql.Blob;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UsuarioDTO {
	private String correo;
	private String password;
	private String nombre;
	private String apellidos;
	private Long telefono;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;
	private Blob foto;
	private Long nDocumento;
}
