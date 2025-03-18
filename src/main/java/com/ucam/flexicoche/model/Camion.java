package com.ucam.flexicoche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "camion")
@PrimaryKeyJoinColumn(name = "id_vehiculo")
@Getter
@Setter
public class Camion extends Vehiculo{

	@Column(name = "peso_max")
	private float pesoMax;
	
	@Column(name = "altura")
	private float altura;
	
	@Column(name = "n_remolques")
	private int numRemolques;

	@Column(name = "tipo_carga")
	private String tipoCarga;
	
	@Column(name = "matricula_rem")
	private String matriculaRemolque;
	
}
