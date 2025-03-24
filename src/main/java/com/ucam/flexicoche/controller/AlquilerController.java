package com.ucam.flexicoche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.service.AlquilerService;

@RestController
@RequestMapping("/alquileres")
public class AlquilerController {

	@Autowired
	private AlquilerService alquilerService;
	
	@GetMapping
	public ResponseEntity<List<AlquilerDTO>> getAlquileres(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
		if (authentication == null || authentication.getName() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		String correo = authentication.getName();
		
		return ResponseEntity.ok(alquilerService.findByCorreo(correo));
	}
}
