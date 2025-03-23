package com.ucam.flexicoche.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Furgoneta;
import com.ucam.flexicoche.model.Moto;
import com.ucam.flexicoche.model.Camion;

import javax.persistence.criteria.*;

public class VehiculoSpecification {
	
  public static Specification<Vehiculo> filtrar(String tipo, String marca, String modelo, String localizacion, String color, String combustible, Long nPlazas, 
		  										String transmision, Long precioMin, Long precioMax) {
        return (Root<Vehiculo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            // Filtrar por la subclase específica (Coche, Moto, Camion, Furgoneta)
            if (tipo != null && !tipo.isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.type(), getTipoClase(tipo)));
                /*
                 *  Class<? extends Vehiculo> tipoClase = getTipoClase(tipo);
            	    if (tipoClase != null) {
            	        predicate = cb.and(predicate, cb.equal(root.get("dtype"), tipoClase.getSimpleName()));
            	    } 
                 * 
                 * */
            }

            if (marca != null && !marca.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("marca")), "%" + marca.toLowerCase() + "%"));
            }
            if (modelo != null && !modelo.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("modelo")), "%" + modelo.toLowerCase() + "%"));
            }
            if (localizacion != null && !localizacion.isEmpty()) {
                predicate = cb.and(predicate, cb.equal(cb.lower(root.get("localizacion")), localizacion.toLowerCase()));
            }
            if (color != null && !color.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("color")), "%" + color.toLowerCase() + "%"));
            }
            if (combustible != null && !combustible.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("combustible")), "%" + combustible.toLowerCase() + "%"));
            }
            if (nPlazas != null) {
                predicate = cb.and(predicate, cb.equal(root.get("nPlazas"), nPlazas));
            }
            if (transmision != null && !transmision.isEmpty()) {
                predicate = cb.and(predicate, cb.equal(cb.lower(root.get("transmision")), transmision.toLowerCase()));
            }
            if (precioMin != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("precioDia"), precioMin));
            }
            if (precioMax != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("precioDia"), precioMax));
            }

            return predicate;
        };
    }

    // Método para mapear el tipo de vehículo con su clase
    private static Class<? extends Vehiculo> getTipoClase(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "coche" -> Coche.class;
            case "moto" -> Moto.class;
            case "camion" -> Camion.class;
            case "furgoneta" -> Furgoneta.class;
            default -> Vehiculo.class;
        };
    }
}
