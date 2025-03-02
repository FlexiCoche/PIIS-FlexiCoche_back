package com.ucam.flexicoche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/{correo}")
	public Usuario getUsuariosByNombre(
			@PathVariable String correo) {
		return usuarioService.findByCorreo(correo);
	}

}
