package com.ucam.flexicoche.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.repository.VehiculoRepository;
import com.ucam.flexicoche.service.VehiculoService;

@Service
public class VehiculoServiceImpl implements VehiculoService{

	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Override
	public List<Vehiculo> getVehiculos() {
		return vehiculoRepository.findAll();
	}

	@Override
	public Vehiculo findVehiculoByMatricula(String matricula) {
		return vehiculoRepository.findByMatricula(matricula);
	}
	
	@Override
	public Vehiculo setVehiculo(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public Vehiculo updateVehiculo(String matricula, String color, Float precio) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMatricula(matricula);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo con matrícula: " + matricula + " no encontrado. Prueba con otra matrícula.");
		}
		
		vehiculoSelect.setColor(color);
		vehiculoSelect.setPrecioDia(precio);
		
		return vehiculoRepository.save(vehiculoSelect);
	}

	@Override
	public Vehiculo updateStateVehiculo(String matricula, int disponibilidad) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMatricula(matricula);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo con matrícula: " + matricula + " no encontrado. Prueba con otra matrícula.");
		}
		
		vehiculoSelect.setDisponibilidad(disponibilidad);
		
		return vehiculoRepository.save(vehiculoSelect);
	}

	@Override
	public void deleteVehiculo(String matricula) {
		vehiculoRepository.deleteByMatricula(matricula);
	}
	


	@Override
	public Coche updateVehiculoCoche(String matricula, String carroceria, int puertas, int potencia) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMatricula(matricula);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo con matrícula: " + matricula + " no encontrado. Prueba con otra matrícula.");
		}
		
		if (vehiculoSelect instanceof Coche) {
			Coche coche = (Coche) vehiculoSelect;
			coche.setCarroceria(carroceria);
			coche.setPuertas(puertas);
			coche.setPotencia(potencia);
			
			return vehiculoRepository.save(coche);
		} else {
			throw new RuntimeException("Vehículo con matrícula: " + matricula + " no es un coche. Prueba con otra matrícula.");
		}
		
	}

	@Override
	public Coche updateVehiculoCochePotencia(String matricula, int potencia) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMatricula(matricula);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo con matrícula: " + matricula + " no encontrado. Prueba con otra matrícula.");
		}
		
		if (vehiculoSelect instanceof Coche) {
			Coche coche = (Coche) vehiculoSelect;
			coche.setPotencia(potencia);
			
			return vehiculoRepository.save(coche);
		} else {
			throw new RuntimeException("Vehículo con matrícula: " + matricula + " no es un coche. Prueba con otra matrícula.");
		}
	}

}
