package com.front.prev.config;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.prev.client.UserClient;
import com.front.prev.dto.ResponseDTO;
import com.front.prev.entity.UserEntity;
import com.front.prev.util.PrevUtil;

@Component
public class CustomAuthenticationProvider
implements AuthenticationProvider {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private CustomPasswordEncoder customEncoder;

	private UserEntity user = null;
	
	ObjectMapper om = new ObjectMapper();
	
    @SuppressWarnings("null")
	@Override
    public Authentication authenticate(Authentication authentication) 
    throws AuthenticationException {	
			String password = "";
	        String name = authentication.getName();
	        if (authentication.getCredentials() != null) {
	        	password = authentication.getCredentials().toString();
			}
	        ResponseDTO response = userClient.getUserLogin(name, password);
        	try {
				user = om.readValue(om.writeValueAsBytes(response.getData()), UserEntity.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        if (Objects.nonNull(user) && matches(password, user.getPasswd())) {
				return new UsernamePasswordAuthenticationToken(user, authentication, user.getAuthorities()) ;
			} else {
				throw new BadCredentialsException("usuario no valido");
			}

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
        UsernamePasswordAuthenticationToken.class);
    }
    
	public boolean matches(CharSequence input, String encodedPass) {
		if (getMD5(input).equals(encodedPass)) {
			return true;
		}
		return false;
	}
	
	private static String getMD5(CharSequence input)
	{
	    try
	    {
	    java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	    byte[] messageDigest = md.digest(((String) input).getBytes());
	    java.math.BigInteger number = new java.math.BigInteger(1, messageDigest);
	    String hashtext = number.toString(16);

	    while (hashtext.length() < 32) {
	        hashtext = "0" + hashtext;
	    }
	    return hashtext;
	    } catch (java.security.NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	    }
	}
    
}