package com.ucam.flexicoche.specification;

import org.springframework.data.jpa.domain.Specification;
import com.ucam.flexicoche.model.*;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.LocalDate;

public class VehiculoSpecification {

    public static Specification<Vehiculo> filtrar(String tipo, String marca, String modelo, String localizacion, String color,
                                                  String combustible, Long nPlazas, String transmision,
                                                  Long precioMin, Long precioMax,
                                                  LocalDate fechaInicio, LocalDate fechaFin) {
        return (Root<Vehiculo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Join<Vehiculo, Alquiler> alquilerJoin = root.join("alquileres", JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (isValid(tipo)) {
                predicate = cb.and(predicate, cb.equal(root.type(), getTipoClase(tipo)));
            }

            if (isValid(marca)) {
                String cleaned = marca.trim().toLowerCase();
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("marca")), "%" + cleaned + "%"));
            }

            if (isValid(modelo)) {
                String cleaned = modelo.trim().toLowerCase();
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("modelo")), "%" + cleaned + "%"));
            }

            if (isValid(localizacion)) {
                String cleaned = localizacion.trim().toLowerCase();
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("localizacion").get("descripcion")), "%" + cleaned + "%"));


            }

            if (isValid(color)) {
                String cleaned = color.trim().toLowerCase();
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("color")), "%" + cleaned + "%"));
            }

            if (isValid(combustible)) {
                String cleaned = combustible.trim().toLowerCase();
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("combustible")), "%" + cleaned + "%"));
            }

            if (nPlazas != null) {
                predicate = cb.and(predicate, cb.equal(root.get("nPlazas"), nPlazas));
            }

            if (isValid(transmision)) {
                String cleaned = transmision.trim().toLowerCase();
                predicate = cb.and(predicate, cb.equal(cb.lower(root.get("transmision")), cleaned));
            }

            if (precioMin != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("precioDia"), precioMin));
            }

            if (precioMax != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("precioDia"), precioMax));
            }

            if (fechaInicio != null && fechaFin != null) {
                LocalDate fechaInicioAjustada = fechaInicio.plusDays(1);
                LocalDate fechaFinAjustada = fechaFin.plusDays(1);

                Timestamp fechaInicioTimestamp = Timestamp.valueOf(fechaInicioAjustada.atStartOfDay());
                Timestamp fechaFinTimestamp = Timestamp.valueOf(fechaFinAjustada.atStartOfDay());

                Predicate alquilerDisponible = cb.or(
                        cb.isNull(alquilerJoin.get("estado")),
                        cb.and(
                                cb.or(
                                        cb.lessThanOrEqualTo(alquilerJoin.get("fechaFin"), fechaInicioTimestamp),
                                        cb.greaterThanOrEqualTo(alquilerJoin.get("fechaInicio"), fechaFinTimestamp)
                                ),
                                cb.or(
                                        cb.equal(alquilerJoin.get("estado"), Estado.DEVUELTO),
                                        cb.equal(alquilerJoin.get("estado"), Estado.DENEGADO)
                                )
                        )
                );

                predicate = cb.and(predicate, alquilerDisponible);
            }

            return predicate;
        };
    }

    private static boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static Class<? extends Vehiculo> getTipoClase(String tipo) {
        return switch (tipo.trim().toLowerCase()) {
            case "coche" -> Coche.class;
            case "moto" -> Moto.class;
            case "camion" -> Camion.class;
            case "furgoneta" -> Furgoneta.class;
            default -> Vehiculo.class;
        };
    }
}
