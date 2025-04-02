package com.ucam.flexicoche.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehiculo")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Vehiculo {
	
	@Id
	private Long id;
	
	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "combustible")
	private String combustible;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "precio_dia")
	private Float precioDia;
	
	@Column(name = "anio_matricula")
	private Date anioMatricula;
	
	@Column(name = "disponibilidad")
	private int disponibilidad;
	
	@Column(name = "marca")
	private String marca;
	
	@Column(name = "modelo")
	private String modelo;
	
	@Column(name = "n_plazas")
	private Long nPlazas;
	
	@Column(name = "transmision")
	private String transmision;
	
	@Column(name = "localizacion")
	private String localizacion;
	
	@OneToOne(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
	private ImagenVehiculo imagen;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "vehiculo")
    private List<Alquiler> alquileres;
}
