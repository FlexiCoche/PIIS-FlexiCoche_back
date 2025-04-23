package com.ucam.flexicoche.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@ManyToOne
	@JoinColumn(name = "localizacion", referencedColumnName = "localizacion")
	private Localizacion localizacion;


	@OneToOne(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
	private ImagenVehiculo imagen;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "vehiculo")
    private List<Alquiler> alquileres;
}
