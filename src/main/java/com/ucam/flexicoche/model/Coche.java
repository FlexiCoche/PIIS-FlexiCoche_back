package com.ucam.flexicoche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coche")
@PrimaryKeyJoinColumn(name = "id_vehiculo")
@Getter
@Setter
public class Coche extends Vehiculo{

	@Column(name = "puertas")
	private int puertas;
	
	@Column(name = "carroceria")
	private String carroceria;
	
	@Column(name = "potencia")
	private int potencia;
	
}
