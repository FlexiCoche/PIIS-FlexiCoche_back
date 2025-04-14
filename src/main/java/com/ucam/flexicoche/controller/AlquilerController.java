package com.ucam.flexicoche.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ucam.flexicoche.dto.AlquilerDTO;
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


}
