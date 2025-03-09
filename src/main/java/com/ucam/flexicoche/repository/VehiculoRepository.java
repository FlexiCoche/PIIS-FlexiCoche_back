package com.ucam.flexicoche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ucam.flexicoche.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String>{
	
	Vehiculo findByMatricula(@Param("matricula") String matricula);
	
	void deleteByMatricula(String matricula);
}
