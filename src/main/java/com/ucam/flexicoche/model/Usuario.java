package com.ucam.flexicoche.model;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

	@Id
	@Column(name = "correo")
	private String correo;

	@Column(name = "n_documento")
	private Long nDocumento;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "telefono")
	private Long telefono;

	@Column(name = "fec_nac")
	private Date fechaNacimiento;

	@Column(name = "rol")
	private int rol;

	@Column(name = "foto")
	private Blob foto;

	@Column(name = "passwd")
	private String password;

	public List<String> getRoles() {
		if (rol == 1)
			return Arrays.asList("ADMIN");
		return Arrays.asList("USER");
	}

}
