package com.ucam.flexicoche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "moto")
@PrimaryKeyJoinColumn(name = "id_vehiculo")
@Getter
@Setter
public class Moto extends Vehiculo{

	@Column(name = "cilindrada")
	private int cilindrada;
		
	@Column(name = "baul")
	private int baul;
	
}
