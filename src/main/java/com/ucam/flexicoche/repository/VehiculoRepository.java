package com.ucam.flexicoche.repository;


import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ucam.flexicoche.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String>{
	
	Vehiculo findByMarca(@Param("marca") String marca);
	
	Vehiculo findByMatricula(@Param("matricula") String matricula);
	
	void deleteByMatricula(String matricula);

	List<Vehiculo> findAll(Specification<Vehiculo> filtrar);

}
