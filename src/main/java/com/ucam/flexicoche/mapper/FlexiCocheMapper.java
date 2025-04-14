package com.ucam.flexicoche.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ucam.flexicoche.dto.*;
import com.ucam.flexicoche.model.*;

@Mapper(componentModel = "spring")
public interface FlexiCocheMapper {

	default VehiculoDTO toVehiculoDto(Vehiculo vehiculo) {
		if (vehiculo instanceof Camion) {
			return toCamionDto((Camion) vehiculo);
		}
		if (vehiculo instanceof Coche) {
			return toCocheDto((Coche) vehiculo);
		}
		if (vehiculo instanceof Furgoneta) {
			return toFurgonetaDto((Furgoneta) vehiculo);
		}
		if (vehiculo instanceof Moto) {
			return toMotoDto((Moto) vehiculo);
		}
		return null;
	}

	@Mappings({
			@Mapping(target = "imagenUrl", expression = "java(mapImagen(camion.getImagen()))"),
			@Mapping(target = "localizacion", expression = "java(map(camion.getLocalizacion()))")
	})
	CamionDTO toCamionDto(Camion camion);

	@Mappings({
			@Mapping(target = "imagenUrl", expression = "java(mapImagen(coche.getImagen()))"),
			@Mapping(target = "localizacion", expression = "java(map(coche.getLocalizacion()))")
	})
	CocheDTO toCocheDto(Coche coche);

	@Mappings({
			@Mapping(target = "imagenUrl", expression = "java(mapImagen(furgoneta.getImagen()))"),
			@Mapping(target = "localizacion", expression = "java(map(furgoneta.getLocalizacion()))")
	})
	FurgonetaDTO toFurgonetaDto(Furgoneta furgoneta);

	@Mappings({
			@Mapping(target = "imagenUrl", expression = "java(mapImagen(moto.getImagen()))"),
			@Mapping(target = "localizacion", expression = "java(map(moto.getLocalizacion()))")
	})
	MotoDTO toMotoDto(Moto moto);

	AlquilerDTO toAlquilerDto(Alquiler alquiler);

	List<AlquilerDTO> toAlquileresDto(List<Alquiler> alquileres);

	UsuarioDTO toUsuarioDto(Usuario usuario);

	// Para estado
	default String toEstado(Estado estado) {
		return estado != null ? estado.getNombre() : null;
	}

	// Localización → descripción
	default String map(Localizacion localizacion) {
		return localizacion != null ? localizacion.getDescripcion() : null;
	}

	// Imagen → URL
	default String mapImagen(ImagenVehiculo imagen) {
		return imagen != null && imagen.getImagen() != null
				? "/assets/images/" + imagen.getImagen()
				: "/assets/images/default.png";
	}

}
