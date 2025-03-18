package com.ucam.flexicoche.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.model.Camion;
import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.ImagenVehiculo;
import com.ucam.flexicoche.model.Moto;
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
	public Vehiculo findVehiculoByNombre(String nombre) {
		return vehiculoRepository.findByNombre(nombre);
	}
	
	@Override
	public Vehiculo findVehiculoByMatricula(String matricula) {
		return vehiculoRepository.findByMatricula(matricula);
	}
	
	/*@Override
	public List<Vehiculo> findVehiculoByDisponibilidad(LocalDate fecha) {
		return vehiculoRepository.findByDisponibilidad(fecha);
	}*/
	
	@Override
	public Vehiculo setVehiculo(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public Vehiculo updateVehiculo(String nombre, String color, Float precio) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByNombre(nombre);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + nombre + " no encontrado. Prueba con otra matrícula.");
		}
		
		vehiculoSelect.setColor(color);
		vehiculoSelect.setPrecioDia(precio);
		
		return vehiculoRepository.save(vehiculoSelect);
	}

	@Override
	public Vehiculo updateStateVehiculo(String nombre, int disponibilidad) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByNombre(nombre);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + nombre + " no encontrado. Prueba con otra matrícula.");
		}
		
		vehiculoSelect.setDisponibilidad(disponibilidad);
		
		return vehiculoRepository.save(vehiculoSelect);
	}

	@Override
	public void deleteVehiculo(String matricula) {
		vehiculoRepository.deleteByMatricula(matricula);
	}
	


	@Override
	public Coche updateVehiculoCoche(String nombre, String carroceria, int puertas, int potencia) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByNombre(nombre);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + nombre + " no encontrado. Prueba con otra matrícula.");
		}
		
		if (vehiculoSelect instanceof Coche) {
			Coche coche = (Coche) vehiculoSelect;
			coche.setCarroceria(carroceria);
			coche.setPuertas(puertas);
			coche.setPotencia(potencia);
			
			return vehiculoRepository.save(coche);
		} else {
			throw new RuntimeException("Vehículo con matrícula: " + nombre + " no es un coche. Prueba con otra matrícula.");
		}
		
	}

	@Override
	public Coche updateVehiculoCochePotencia(String nombre, int potencia) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByNombre(nombre);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + nombre + " no encontrado. Prueba con otra matrícula.");
		}
		
		if (vehiculoSelect instanceof Coche) {
			Coche coche = (Coche) vehiculoSelect;
			coche.setPotencia(potencia);
			
			return vehiculoRepository.save(coche);
		} else {
			throw new RuntimeException("Vehículo con matrícula: " + nombre + " no es un coche. Prueba con otra matrícula.");
		}
	}

	@Override
	public Vehiculo updateVehiculoImagenDesdeURL(String matricula, String imageUrl) {
		Vehiculo vehiculo = vehiculoRepository.findByMatricula(matricula);

	    if (vehiculo == null) {
	        throw new RuntimeException("Vehículo no encontrado con matrícula: " + matricula);
	    }

        ImagenVehiculo imagenVehiculo = vehiculo.getImagen();
        if (imagenVehiculo == null) {
            imagenVehiculo = new ImagenVehiculo();
            imagenVehiculo.setVehiculo(vehiculo);
            vehiculo.setImagen(imagenVehiculo);
        }

        imagenVehiculo.setImagen(imageUrl);

        return vehiculoRepository.save(vehiculo);
	}

}
