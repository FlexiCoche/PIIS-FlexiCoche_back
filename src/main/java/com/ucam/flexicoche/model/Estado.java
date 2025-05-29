package com.ucam.flexicoche.model;

public enum Estado {

	A_PAGAR("a pagar"),
	PROCESANDO("procesando"),
	DENEGADO("denegado"),
	EN_ALQUILER("en alquiler"),
	DEVUELTO("devuelto"),
	RETRASO("retraso");

	private String nombre;
	 
    private Estado(String nombre) {
        this.nombre = nombre;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public static Estado fromNombre(String nombre) {
        switch (nombre) {
        case "a pagar":
            return Estado.A_PAGAR;
 
        case "procesando":
            return Estado.PROCESANDO;
 
        case "denegado":
            return Estado.DENEGADO;
 
        case "en alquiler":
            return Estado.EN_ALQUILER;
            
        case "devuelto":
            return Estado.DEVUELTO;
            
        case "retraso":
            return Estado.RETRASO;
 
        default:
            throw new IllegalArgumentException("Estado [" + nombre
                    + "] not supported.");
        }
    }
}
