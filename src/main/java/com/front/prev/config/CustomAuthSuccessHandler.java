package com.front.prev.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.front.prev.client.UserClient;
import com.front.prev.dto.EmailDTO;
import com.front.prev.entity.UserEntity;
import com.front.prev.util.JwtToken;
import com.front.prev.util.PrevUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserClient userClient;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		UserEntity user = PrevUtil.getUser();
		String redirect = "/loginsuccess";
    	if(user.getVerified().equals("N")){
    		String token = JwtToken.generateToken(user.getIdUser());
    		userClient.sendEmailVerifed(EmailDTO.builder().urlToken(token).email(user.getEmail()).build());
    		redirect = "/login?noVerifed";
    		response.sendRedirect(redirect);
    	}else {
    		session.setAttribute("userSession", user);
        	response.sendRedirect(redirect);
    	}
	}
	
}
