package com.ucam.flexicoche.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.dto.UsuarioModDTO;
import com.ucam.flexicoche.mapper.FlexiCocheMapper;
import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.repository.UsuarioRepository;
import com.ucam.flexicoche.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FlexiCocheMapper mapper;

	@Override
	public Usuario findByCorreo(String correo) {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
		if (usuario.isPresent()) {
			return usuario.get();
		}
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

	@Override
	public UsuarioDTO recuperarDatos(String correo) {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
		if (usuario.isPresent()) {
			return mapper.toUsuarioDto(usuario.get());
		}
		return null;
	}

	@Override
	public void actualizarUsuario(String correo, UsuarioModDTO usuarioMod) throws Exception {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();

			if (usuario.getApellidos() == null && usuarioMod.getApellidos() != null
					|| usuario.getApellidos() != null && !usuarioMod.getApellidos().equals(usuario.getApellidos())) {
				usuario.setApellidos(usuarioMod.getApellidos());
			}
			if (usuario.getNombre() == null && usuarioMod.getNombre() != null
					|| usuario.getNombre() != null && !usuarioMod.getNombre().equals(usuario.getNombre())) {
				usuario.setNombre(usuarioMod.getNombre());
			}
			if (usuario.getFechaNacimiento() == null && usuarioMod.getFechaNacimiento() != null
					|| usuario.getFechaNacimiento() != null
							&& !usuarioMod.getFechaNacimiento().equals(usuario.getFechaNacimiento())) {
				usuario.setFechaNacimiento(usuarioMod.getFechaNacimiento());
			}
			if (usuario.getNDocumento() == null && usuarioMod.getNDocumento() != null
					|| usuario.getNDocumento() != null && !usuarioMod.getNDocumento().equals(usuario.getNDocumento())) {
				usuario.setNDocumento(usuarioMod.getNDocumento());
			}
			if (usuario.getTelefono() == null && usuarioMod.getTelefono() != null
					|| usuario.getTelefono() != null && !usuarioMod.getTelefono().equals(usuario.getTelefono())) {
				usuario.setTelefono(usuarioMod.getTelefono());
			}

			usuarioRepository.save(usuario);
		} else {
			throw new Exception("Usuario no encontrado");
		}
	}
}