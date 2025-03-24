package com.ucam.flexicoche.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ucam.flexicoche.dto.AlquilerDTO;
import com.ucam.flexicoche.dto.CamionDTO;
import com.ucam.flexicoche.dto.CocheDTO;
import com.ucam.flexicoche.dto.FurgonetaDTO;
import com.ucam.flexicoche.dto.MotoDTO;
import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.dto.VehiculoDTO;
import com.ucam.flexicoche.model.Alquiler;
import com.ucam.flexicoche.model.Camion;
import com.ucam.flexicoche.model.Coche;
import com.ucam.flexicoche.model.Estado;
import com.ucam.flexicoche.model.Furgoneta;
import com.ucam.flexicoche.model.Moto;
import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.model.Vehiculo;

@Mapper
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

	CamionDTO toCamionDto(Camion camion);

	CocheDTO toCocheDto(Coche coche);

	FurgonetaDTO toFurgonetaDto(Furgoneta furgoneta);

	MotoDTO toMotoDto(Moto moto);

	AlquilerDTO toAlquilerDto(Alquiler alquiler);

	List<AlquilerDTO> toAlquileresDto(List<Alquiler> alquileres);

	UsuarioDTO toUsuarioDto(Usuario usuario);

	default String toEstado(Estado estado) {
		return estado.getNombre();
	}

}
