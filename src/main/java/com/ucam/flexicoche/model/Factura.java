package com.ucam.flexicoche.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "factura")
@Getter
@Setter
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    private Double importe;
}
