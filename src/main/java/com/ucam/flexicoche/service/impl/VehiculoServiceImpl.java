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
import com.ucam.flexicoche.specification.VehiculoSpecification;



@Service
public class VehiculoServiceImpl implements VehiculoService{

	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Override
	public List<Vehiculo> getVehiculos() {
		return vehiculoRepository.findAll(); 
	}

	@Override
	public Vehiculo findVehiculoByMarca(String marca) {
		return vehiculoRepository.findByMarca(marca);
	}
	
	@Override
	public Vehiculo findVehiculoByMatricula(String matricula) {
		return vehiculoRepository.findByMatricula(matricula);
	}
	
	 public List<Vehiculo> buscarVehiculos(String tipo, String marca, String modelo, String localizacion, String color, String combustible, Long nPlazas, 
			 								String transmision, Long precioMin, Long precioMax) {
	    return vehiculoRepository.findAll(VehiculoSpecification.filtrar(tipo, marca, modelo, localizacion, color, combustible, nPlazas, transmision, precioMin, precioMax));
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
	public Vehiculo updateVehiculo(String marca, String color, Float precio) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMarca(marca);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + marca + " no encontrado. Prueba con otra matrícula.");
		}
		
		vehiculoSelect.setColor(color);
		vehiculoSelect.setPrecioDia(precio);
		
		return vehiculoRepository.save(vehiculoSelect);
	}

	@Override
	public Vehiculo updateStateVehiculo(String marca, int disponibilidad) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMarca(marca);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + marca + " no encontrado. Prueba con otra matrícula.");
		}
		
		vehiculoSelect.setDisponibilidad(disponibilidad);
		
		return vehiculoRepository.save(vehiculoSelect);
	}

	@Override
	public void deleteVehiculo(String matricula) {
		vehiculoRepository.deleteByMatricula(matricula);
	}
	


	@Override
	public Coche updateVehiculoCoche(String marca, String carroceria, int puertas, int potencia) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMarca(marca);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + marca + " no encontrado. Prueba con otra matrícula.");
		}
		
		if (vehiculoSelect instanceof Coche) {
			Coche coche = (Coche) vehiculoSelect;
			coche.setCarroceria(carroceria);
			coche.setPuertas(puertas);
			coche.setPotencia(potencia);
			
			return vehiculoRepository.save(coche);
		} else {
			throw new RuntimeException("Vehículo con matrícula: " + marca + " no es un coche. Prueba con otra matrícula.");
		}
		
	}

	@Override
	public Coche updateVehiculoCochePotencia(String marca, int potencia) {
		Vehiculo vehiculoSelect = vehiculoRepository.findByMarca(marca);
		
		if (vehiculoSelect == null) {
			throw new RuntimeException("Vehículo: " + marca + " no encontrado. Prueba con otra matrícula.");
		}
		
		if (vehiculoSelect instanceof Coche) {
			Coche coche = (Coche) vehiculoSelect;
			coche.setPotencia(potencia);
			
			return vehiculoRepository.save(coche);
		} else {
			throw new RuntimeException("Vehículo con matrícula: " + marca + " no es un coche. Prueba con otra matrícula.");
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
