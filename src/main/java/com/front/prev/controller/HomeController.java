package com.front.prev.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.front.prev.entity.UserEntity;
import com.front.prev.util.PrevUtil;
import com.front.prev.util.ViewConstants;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public ModelAndView showHome(HttpSession session, @RequestParam(name = "idioma", required = false) String idioma,
		@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie) {
		ModelAndView mv = new ModelAndView(ViewConstants.HOME);
		UserEntity user = PrevUtil.getUser();
		if (idioma != null) {	
			locateCookie = idioma;
		}
		
		String titulo = "Home";
		mv.addObject("titulo", titulo);
		mv.addObject("locateCookie", locateCookie);
		mv.addObject("user", user);
		return mv;
	}
	
	
	
}
