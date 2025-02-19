package com.ucam.flexicoche.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nombre_usuario")
	private String nombreUsuario;
	
	@Column(name="contrasena")
	private String contrasena;
	
	@Column(name="correo_electronico")
	private String correo_electronico;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido1")
	private String apellido1;
	
	@Column(name="apellido2")
	private String apellido2;
	
	@Column(name="numero_telefono")
	private String numero_telefono;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="fecha_creacion")
	private Timestamp fecha_creacion;

}
