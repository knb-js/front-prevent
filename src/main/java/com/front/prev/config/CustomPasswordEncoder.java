package com.front.prev.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence input) {
		return getMD5(input);
	}

	@Override
	public boolean matches(CharSequence input, String encodedPass) {
		if (getMD5(input).equals(encodedPass)) {
			return true;
		}
		return false;
	}

	private String getMD5(CharSequence input) {
		try {
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