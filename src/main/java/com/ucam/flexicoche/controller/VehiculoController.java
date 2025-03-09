package com.ucam.flexicoche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.service.impl.VehiculoServiceImpl;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

	@Autowired
	private VehiculoServiceImpl vehiculoServiceImpl;

	public ResponseEntity<List<Vehiculo>> getVehiculos() {
		return ResponseEntity.ok(vehiculoServiceImpl.getVehiculos());
	}
	
	@GetMapping("/{matricula}")
	public ResponseEntity<Vehiculo> getVehiculoMatricula(@PathVariable("matricula") String matricula) {
		return ResponseEntity.ok(vehiculoServiceImpl.findVehiculoByMatricula(matricula));
	}
	
	@PostMapping("/add")
	public ResponseEntity<Vehiculo> setVehiculo(@RequestBody Vehiculo vehiculo) {
		return ResponseEntity.ok(vehiculoServiceImpl.setVehiculo(vehiculo));
	}
	
	@PutMapping("/updateVehiculo/{matricula}")
	public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable("matricula") String matricula, @RequestParam String color,  @RequestParam Float precio){
		Vehiculo vehiculoActualizado = vehiculoServiceImpl.updateVehiculo(matricula, color, precio);
		
		return ResponseEntity.ok(vehiculoActualizado);
	}
	
	@PutMapping("/updateDisponibilidad/{matricula}")
	public ResponseEntity<Vehiculo> updateVehiculoDisponibilidad(@PathVariable("matricula") String matricula, @RequestParam int disponibilidad) {
		Vehiculo vehiculoActualizado = vehiculoServiceImpl.updateStateVehiculo(matricula, disponibilidad);
		
		return ResponseEntity.ok(vehiculoActualizado);
	}
	
	@DeleteMapping("/{matricula}")
	public void deleteVehiculo(@PathVariable("matricula") String matricula) {
		vehiculoServiceImpl.deleteVehiculo(matricula);
	}
	
	
	@PutMapping("/updateVehiculoCoche/{matricula}")
	public ResponseEntity<Coche> updateVehiculoCoche(@PathVariable("matricula") String matricula, @RequestParam String carroceria,  
													@RequestParam int puertas,  @RequestParam int potencia){
		Coche vehiculoActualizado = vehiculoServiceImpl.updateVehiculoCoche(matricula, carroceria, puertas, potencia);
		
		return ResponseEntity.ok(vehiculoActualizado);
	}
	
	@PutMapping("/updatePotencia/{matricula}")
	public ResponseEntity<Coche> updateVehiculoCochePotencia(@PathVariable("matricula") String matricula, @RequestParam int potencia) {
		Coche vehiculoActualizado = vehiculoServiceImpl.updateVehiculoCochePotencia(matricula, potencia);
		
		return ResponseEntity.ok(vehiculoActualizado);
	}
	
}
