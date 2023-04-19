package com.front.prev.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.front.prev.entity.UserEntity;

@Component
public class PrevUtil {

	public static UserEntity getUser() {
		UserEntity user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object myUser = (auth != null) ? auth.getPrincipal() : null;
		if (myUser instanceof UserEntity) {
			user = (UserEntity) myUser;
		}
		return user;
	}
	
	public static UserEntity getUserSession(HttpSession session) {
		UserEntity userSession =  (UserEntity) session.getAttribute("userSession");
		return userSession;
	}
	
	public String getBCrypt(String input) {
		try {
			return new BCryptPasswordEncoder().encode(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
