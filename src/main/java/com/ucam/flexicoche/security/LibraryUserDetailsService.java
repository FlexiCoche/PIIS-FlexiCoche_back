package com.ucam.flexicoche.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ucam.flexicoche.model.Usuario;
import com.ucam.flexicoche.service.UsuarioService;

@Service
public class LibraryUserDetailsService implements UserDetailsService {
	@Autowired
	private UsuarioService usuarioService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.findByCorreo(correo);
		if (usuario == null)
			throw new UsernameNotFoundException("Correo/contraseña erróneos");

		List<GrantedAuthority> authorities = getUserAuthority(usuario.getRoles());
		return buildUserForAuthentication(usuario, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(List<String> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role)));
		return new ArrayList<>(roles);
	}

	private UserDetails buildUserForAuthentication(Usuario usuario, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getPassword(), true,
				true, true, true, authorities);
	}
}
