package com.ucam.flexicoche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "furgoneta")
@PrimaryKeyJoinColumn(name = "id_vehiculo")
@Getter
@Setter
public class Furgoneta extends Vehiculo{

	@Column(name = "volumen")
	private float volumen;
	
	@Column(name = "longitud")
	private float longitud;
	
	@Column(name = "peso_max")
	private float pesoMax;
	
}
