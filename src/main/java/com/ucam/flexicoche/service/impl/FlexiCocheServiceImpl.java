package com.ucam.flexicoche.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.service.FlexiCocheService;

@Service
public class FlexiCocheServiceImpl implements FlexiCocheService{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> getUsuarios(String nombreUsuario) {
		return usuarioRepository.findByNombreUsuario(nombreUsuario);
	}
	
	@Override
	public List<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}
	
}
