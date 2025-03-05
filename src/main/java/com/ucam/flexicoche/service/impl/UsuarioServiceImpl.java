package com.ucam.flexicoche.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario findByCorreo(String correo) {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
		if (usuario.isPresent())
			return usuario.get();
		return null;
	}

	@Override
	public Usuario addUser(UsuarioDTO usuarioDto) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		Usuario usuario = new Usuario();
		usuario.setCorreo(usuarioDto.getCorreo());
		usuario.setPassword(bCryptPasswordEncoder.encode(usuarioDto.getPassword()));
		usuario.setNDocumento(usuarioDto.getNDocumento());
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellidos(usuarioDto.getApellidos());
		usuario.setTelefono(usuarioDto.getTelefono());
		usuario.setFechaNacimiento(usuarioDto.getFechaNacimiento());
		usuario.setRol(0);

		return usuarioRepository.save(usuario);
	}
}