package com.ucam.flexicoche.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ucam.flexicoche.dto.LocalizacionDTO;
import com.ucam.flexicoche.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

	Vehiculo findByMarca(@Param("marca") String marca);

	Vehiculo findByMatricula(@Param("matricula") String matricula);

	void deleteByMatricula(String matricula);

	//List<Vehiculo> findAll(Specification<Vehiculo> filtrar);

	@org.springframework.data.jpa.repository.Query("SELECT DISTINCT v.localizacion.descripcion FROM Vehiculo v WHERE v.localizacion IS NOT NULL")
	List<String> findAllLocalizacionesUnicas();

	@EntityGraph(attributePaths = "imagen")
	List<Vehiculo> findAll(Specification<Vehiculo> filtrar);

	@Query("SELECT new com.ucam.flexicoche.dto.LocalizacionDTO(l.localizacion, l.descripcion) FROM Localizacion l WHERE l.localizacion IS NOT NULL")
	List<LocalizacionDTO> findAllLocalizacionesUnicasDetalladas();

}
