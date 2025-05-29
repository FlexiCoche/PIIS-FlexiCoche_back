package com.ucam.flexicoche.service;

import org.springframework.web.multipart.MultipartFile;

import com.ucam.flexicoche.dto.PasswordDTO;
import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.dto.UsuarioModDTO;
import com.ucam.flexicoche.model.Usuario;

public interface UsuarioService {

	Usuario findByCorreo(String correo);
	
	Usuario addUser(UsuarioDTO usuarioDto);
	
	UsuarioDTO recuperarDatos(String correo);
	
	void actualizarUsuario(String correo, UsuarioModDTO usuarioMod) throws Exception;
	
	void actualizarPassword(String correo, PasswordDTO password) throws Exception;

	void actualizarImagenUsuario(String correo, MultipartFile imagen) throws Exception;
}
