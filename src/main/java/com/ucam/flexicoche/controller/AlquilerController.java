package com.ucam.flexicoche.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.dto.EstadoDTO;
import com.ucam.flexicoche.dto.RegistrarAlquilerDTO;
import com.ucam.flexicoche.service.AlquilerService;

@RestController
@RequestMapping("/alquileres")
public class AlquilerController {

	@Autowired
	private AlquilerService alquilerService;

	@GetMapping
	public ResponseEntity<List<AlquilerDTO>> getAlquileres() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		return ResponseEntity.ok(alquilerService.findByCorreo(correo));
	}

	@PostMapping
	public ResponseEntity<?> registrarAlquiler(@RequestBody RegistrarAlquilerDTO alquiler) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		try {
			alquilerService.registrarAlquiler(correo, alquiler);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idAlquiler}")
	public ResponseEntity<?> cancelarAlquiler(@PathVariable Long idAlquiler) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		try {
			alquilerService.cancelarAlquiler(correo, idAlquiler);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}/pagar")
	public ResponseEntity<Map<String, String>> pagarAlquiler(@PathVariable Long id) {
		try {
			alquilerService.pagarAlquiler(id);
			return ResponseEntity.ok(Map.of("estado", "procesando"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> anularAlquiler(@PathVariable Long id) {
		try {
			alquilerService.anularAlquiler(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/cambiarEstado")
	public ResponseEntity<?> cambiarEstado(@RequestBody EstadoDTO estado) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String correo = authentication.getName();

		try {
			alquilerService.cambiarEstado(correo, estado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
}
