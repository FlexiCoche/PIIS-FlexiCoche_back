package com.ucam.flexicoche.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ucam.flexicoche.dto.UsuarioDTO;
import com.ucam.flexicoche.security.JwtResponse;
import com.ucam.flexicoche.security.JwtUtils;
import com.ucam.flexicoche.service.UsuarioService;

@RestController
public class TokenController {
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/token")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(usuarioDTO.getCorreo(), usuarioDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		User userDetails = (User) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody UsuarioDTO usuario) throws Exception {
		return ResponseEntity.ok(usuarioService.addUser(usuario));
	}
}
