package com.ucam.flexicoche.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "localizacion")
@Getter
@Setter
public class Localizacion {

    @Id
    private Integer localizacion;

    @Column(name = "descripcion")
    private String descripcion;
}
