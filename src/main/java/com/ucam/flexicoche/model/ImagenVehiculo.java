package com.ucam.flexicoche.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"vehiculo"})
@Entity
@Table(name = "imagen")
@PrimaryKeyJoinColumn(name = "id_vehiculo")
@Getter
@Setter
public class ImagenVehiculo{

    @Id
    private Long id_vehiculo;
    
	@Lob
	@Column(name = "imagen", length = 2000)
	private String imagen;


    @OneToOne
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id")
    private Vehiculo vehiculo;

}