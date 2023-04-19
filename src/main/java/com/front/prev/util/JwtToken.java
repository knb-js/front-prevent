package com.front.prev.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken {
	
	private static String secret = "ASDLASDJ!";
	
    public static String generateToken(Integer idUsuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", idUsuario);
        return createToken(claims, idUsuario);
    }
    
    public static String generateTokenEmail(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return createTokenEmail(claims, email);
    }
    
    public static String createToken(Map<String, Object> claims, Integer numRandom) {
    	
        return Jwts.builder().setClaims(claims).setSubject(numRandom.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
		        .setExpiration(new Date(System.currentTimeMillis() + 500000))
		        .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    
    public static String createTokenEmail(Map<String, Object> claims, String email) {
    	
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
		        .setExpiration(new Date(System.currentTimeMillis() + 500000))
		        .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
    public static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    static ObjectMapper om = new ObjectMapper();
    
	public static Integer decodeToken(String token) {
		try {
			JwtToken.isTokenExpired(token);
			Claims claims = Jwts.parser()
	        .setSigningKey(secret)
	        .parseClaimsJws(token)
	        .getBody();
			Integer userToken = (Integer) claims.get("idUsuario");
			return userToken;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	public static String decodeTokenMail(String token) {
		try {
			JwtToken.isTokenExpired(token);
			Claims claims = Jwts.parser()
	        .setSigningKey(secret)
	        .parseClaimsJws(token)
	        .getBody();
			return (String) claims.get("email");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
}
