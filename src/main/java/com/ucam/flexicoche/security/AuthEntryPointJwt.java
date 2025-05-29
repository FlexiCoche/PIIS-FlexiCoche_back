package com.ucam.flexicoche.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

		if (isHtmlRequest(request)) {
			response.sendRedirect("/index.html");
		} else {
			sendJsonError(response);
		}
	}

	private void sendJsonError(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("{\"error\": \"Acceso no autorizado\"}");
	}

	private boolean isHtmlRequest(HttpServletRequest request) {
		String acceptHeader = request.getHeader("Accept");
		return acceptHeader != null && acceptHeader.contains("text/html");
	}
}

