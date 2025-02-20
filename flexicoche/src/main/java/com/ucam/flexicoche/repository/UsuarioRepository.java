package com.ucam.flexicoche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ucam.flexicoche.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional findById(@Param("id") Long id);
	
	List<Usuario> findByNombreUsuario(@Param("nombre_usuario") String nombre);
}
