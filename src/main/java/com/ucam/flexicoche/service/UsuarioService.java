package com.ucam.flexicoche.service;

import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.model.Usuario;

public interface UsuarioService {

	Usuario findByCorreo(String correo);
	
	Usuario addUser(UsuarioDTO usuarioDto);
}
