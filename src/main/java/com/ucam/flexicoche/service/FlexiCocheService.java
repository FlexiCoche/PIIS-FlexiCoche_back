package com.ucam.flexicoche.service;

import java.util.List;

import com.ucam.flexicoche.model.Usuario;

public interface FlexiCocheService {

	public Usuario getUsuarios(String nombreUsuario);
	
	public List<Usuario> getUsuarios();
}
