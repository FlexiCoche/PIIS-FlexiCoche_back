package com.ucam.flexicoche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.service.FlexiCocheService;

@RestController
@RequestMapping("/flexicoche")
public class UsuarioController {

	@Autowired
	private FlexiCocheService flexiCocheService;

	@GetMapping("usuarios/{nombre}")
	public List<Usuario> getUsuariosByNombre(
			@PathVariable(name = "nombre") String nombreUsuario) {
		return flexiCocheService.getUsuarios(nombreUsuario);
	}

	@GetMapping("usuarios")
	public List<Usuario> getUsuarios() {
		return flexiCocheService.getUsuarios();
	}

}
