package com.ucam.flexicoche.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.service.FlexiCocheService;

@Service
public class FlexiCocheServiceImpl implements FlexiCocheService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario getUsuarios(String nombreUsuario) {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(nombreUsuario);
		if (usuario.isPresent())
			return usuario.get();
		return null;
	}

	@Override
	public List<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}

}
