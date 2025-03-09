package com.ucam.flexicoche.service;

import java.util.List;

import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Vehiculo;
import com.ucam.flexicoche.dto.VehiculoDTO;

public interface VehiculoService {

	/*-------- Metodos Vehiculo -------*/
	
	// Lista todos los vehiculos
	List<Vehiculo> getVehiculos();
	
	// Lista vehiculo con X matricula
	Vehiculo findVehiculoByMatricula(String matricula);
	
	// Guarda vehiculo
	Vehiculo setVehiculo(Vehiculo vehiculo);
	
	// Actualizar campos vehiculo (color, precio)
	Vehiculo updateVehiculo(String matricula, String color, Float precio);
	
	// Actualizar estado vehiculo
	Vehiculo updateStateVehiculo(String matricula, int disponibilidad);
	
	// Eliminar vehiculo
	public void deleteVehiculo(String matricula);
	

	/*-------- Metodos Coche -------*/
	
	// Actualizar campos coche
	Coche updateVehiculoCoche(String matricula, String carroceria, int puertas, int potencia);
	
	// Actualizar campos coche
	Coche updateVehiculoCochePotencia(String matricula, int potencia);

}
