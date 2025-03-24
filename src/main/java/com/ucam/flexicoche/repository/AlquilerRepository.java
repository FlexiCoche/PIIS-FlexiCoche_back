package com.ucam.flexicoche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucam.flexicoche.model.Alquiler;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long>{

	@Query("SELECT a FROM Alquiler a where a.usuario.correo = :correo")
	List<Alquiler> findByUsuario(String correo); 
}
