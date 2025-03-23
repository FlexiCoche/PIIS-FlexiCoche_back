package com.ucam.flexicoche.service;

import java.util.List;

import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Vehiculo;

public interface VehiculoService {

	/*-------- Metodos Vehiculo -------*/
	
	// Lista todos los vehiculos
	List<Vehiculo> getVehiculos();
	
	// Lista vehiculo con X modelo
	Vehiculo findVehiculoByMarca(String marca);
	
	Vehiculo findVehiculoByMatricula(String matricula);
	
	// Lista vehiculo con disponibilidad
	/*List<Vehiculo> findVehiculoByDisponibilidad(LocalDate fecha);*/
		
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

	/*-------- Metodos Imagen Vehiculo -------*/

	// Actualizar imagen
	Vehiculo updateVehiculoImagenDesdeURL(String matricula, String imageUrl);


}
