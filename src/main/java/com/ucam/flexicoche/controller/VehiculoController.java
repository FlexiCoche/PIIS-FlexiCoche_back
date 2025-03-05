package com.ucam.flexicoche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.service.VehiculoService;

@RestController
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;

	@GetMapping("/vehiculos")
	public ResponseEntity<List<Vehiculo>> getVehiculos() {
		return ResponseEntity.ok(vehiculoService.getVehiculos());
	}
}
