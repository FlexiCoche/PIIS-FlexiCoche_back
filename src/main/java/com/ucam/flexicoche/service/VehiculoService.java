package com.ucam.flexicoche.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ucam.flexicoche.model.Camion;
import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Furgoneta;
import com.ucam.flexicoche.model.Moto;
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
	Vehiculo setVehiculo(String correo, Vehiculo vehiculo);
	
	// Actualizar campos vehiculo (color, precio)
	//Vehiculo updateVehiculo(String matricula, String color, Float precio);
	Vehiculo updateVehiculo(String correo, String matricula, Vehiculo vehiculo);
	
	// Actualizar estado vehiculo
	Vehiculo updateStateVehiculo(String correo, String matricula, int disponibilidad);
	
	// Eliminar vehiculo
	public void deleteVehiculo(String correo, String matricula);
	

	/*-------- Metodos Coche -------*/
	
	// Actualizar campos coche
	Coche updateVehiculoCoche(String correo, String matricula, String carroceria, int puertas, int potencia);
		
	/*-------- Metodos Moto -------*/
	
	Moto updateVehiculoMoto(String correo, String matricula, int cilindrada, int baul);

	/*-------- Metodos Furgoneta -------*/
	
	Furgoneta updateVehiculoFurgoneta(String correo, String matricula, float volumen, float longitud, float pesoMax);

	/*-------- Metodos Camion -------*/

	Camion updateVehiculoCamion(String correo, String matricula, float altura, int numRemolques, String tipoCarga,
			String matriculaRemolque, float pesoMax);
	
	/*-------- Metodos Imagen Vehiculo -------*/

	// Actualizar imagen
	Vehiculo updateVehiculoImagenDesdeURL(String correo, String matricula, MultipartFile imageUrl) throws Exception;


}
