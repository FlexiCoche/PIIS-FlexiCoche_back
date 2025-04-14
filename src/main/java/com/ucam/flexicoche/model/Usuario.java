package com.ucam.flexicoche.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private String nDocumento;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "telefono")
	private Long telefono;

	@Column(name = "fec_nac")
	private LocalDate fechaNacimiento;

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
