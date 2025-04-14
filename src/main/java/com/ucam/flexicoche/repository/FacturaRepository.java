package com.ucam.flexicoche.repository;

import com.ucam.flexicoche.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByAlquilerId(Long idAlquiler);

}
