package com.ucam.flexicoche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.dto.PasswordDTO;
import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.dto.UsuarioModDTO;
import com.ucam.flexicoche.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/datos")
	public ResponseEntity<UsuarioDTO> getUsuariosByNombre() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		UsuarioDTO datos = usuarioService.recuperarDatos(correo);

		if (datos != null) {
			return ResponseEntity.ok(datos);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@PostMapping
	public ResponseEntity<?> actualizarDatosUsuario(@RequestBody UsuarioModDTO usuario) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		try {
			usuarioService.actualizarUsuario(correo, usuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/modificarPassword")
	public ResponseEntity<?> modificarPassword(@RequestBody PasswordDTO password) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		try {
			usuarioService.actualizarPassword(correo, password);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}

}
