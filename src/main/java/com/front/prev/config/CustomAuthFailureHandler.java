package com.front.prev.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
		String error = "";

		if(exception.getClass() == UsernameNotFoundException.class) {
			error = "cannot find a user";
		} else if(exception.getClass() == BadCredentialsException.class) {
			if (exception.getMessage().contains("captcha")) {
				//System.out.println("Captcha fraudolento");
				error = "1";
			}else if(exception.getMessage().contains("Usuario no valido")){
				//System.out.println("Cliente no valido");
				error = "2";
			}else if(exception.getMessage().contains("Sin locales asignados")){
				//System.out.println("Cliente bloqueado");
				error = "3";
			}else if(exception.getMessage().contains("No existe tipo cambio")){
				//System.out.println("Cliente bloqueado");
				error = "4";
			}
			
		}
		
		response.sendRedirect(String.format("/login?error=%s", error));
	//	request.getRequestDispatcher(String.format("/login?error=%s", error)).forward(request, response);
		
	}
}
