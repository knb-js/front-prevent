package com.front.prev.controller;


import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.front.prev.client.UserClient;
import com.front.prev.dto.EmailDTO;
import com.front.prev.entity.UserEntity;
import com.front.prev.service.ComunaService;
import com.front.prev.service.EstadoCivilService;
import com.front.prev.service.GeneroService;
import com.front.prev.service.NacionalidadService;
import com.front.prev.util.JwtToken;
import com.front.prev.util.PrevUtil;
import com.front.prev.util.ViewConstants;

@Controller
public class LoginController {
	
	@Autowired
	private PrevUtil prevUtil;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private NacionalidadService nacionalidadService;
	
	@Autowired
	private GeneroService generoService;
	
	@Autowired
	private ComunaService comunaService;
	
	@Autowired
	private EstadoCivilService estadoCivilService;
	
	
	@GetMapping("/login")
	public String ShowLoginForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout,
			@RequestParam(name = "idioma", required = false) String idioma,
			@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie ) {
		return ViewConstants.LOGIN;
	}
	
	@GetMapping("/confirmAccount")
	public ModelAndView confirmAccount(Model model, @RequestParam(name = "token") String token,
			@RequestParam(name = "logout", required = false) String logout,
			@RequestParam(name = "idioma", required = false) String idioma,
			@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie ) {

		ModelAndView mv = new ModelAndView(ViewConstants.VERIFED);
		
		Integer idUser = JwtToken.decodeToken(token);
		
		if(Objects.nonNull(idUser)) {
			userClient.updateVerified(JwtToken.decodeToken(token));
			mv.addObject("response", "Tu cuenta ha sido verificada ☺");
			return mv;
		}
		mv.addObject("response", "El token es inválido ☹");
		return mv;
	}
	
	@GetMapping("/recovery-pass")
	public ModelAndView recoveryPass(
			Model model,
			@RequestParam(name = "logout", required = false) String logout,
			@RequestParam(name = "idioma", required = false) String idioma,
			@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie ) {

		ModelAndView mv = new ModelAndView(ViewConstants.RECOVERY);
		return mv;
	}
	
	@GetMapping("/ajax/sendReset/{email}")
	public @ResponseBody Boolean sendCodeUser (
			@PathVariable("email") String email,
			@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie){
		
		String token = JwtToken.generateTokenEmail(email);
		userClient.sendEmailResetPassword(EmailDTO.builder().urlToken(token).email(email).build());
		return true;
	}
	
	@GetMapping("/change-password")
	public ModelAndView changePassword(
			Model model,
			@RequestParam(name = "token") String token,
			@RequestParam(name = "logout", required = false) String logout,
			@RequestParam(name = "idioma", required = false) String idioma,
			@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie) {
		
		ModelAndView mv = new ModelAndView(ViewConstants.CHANGE_PASSWORD);
		return mv;
	}
	
	@GetMapping("/ajax/change-password/{token}/{password}")
	public @ResponseBody Boolean ajaxChangePassword(@PathVariable("token") String token,@PathVariable("password") String password,
			@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie){
		
		String email = JwtToken.decodeTokenMail(token);
		
		if(Objects.nonNull(email)) {
			return userClient.changePassword(UserEntity.builder().email(email).passwd(password).build());
		}
		
		return false;
	}
	
	
	@GetMapping("/loginsuccess")
	public String loginCheck(HttpSession session) {
		UserEntity user = PrevUtil.getUser();
		
		return user.getProfile().getCode().equals("CLI") ? "redirect:/public-home":"redirect:/home";
	}
	
	@GetMapping("/")
	public String redirect() {
		return "redirect:/login";
	}
	
	@GetMapping("/registerNewUser")
	private ModelAndView crearUserFormulario(Model modelo) {
		ModelAndView mv = new ModelAndView(ViewConstants.REGISTER_NEW_USER);
		mv.addObject("findAllNacionalidad",nacionalidadService.findAllNacionalidad());
		mv.addObject("findAllGenero",generoService.findAllGenero());
		mv.addObject("findAllComuna",comunaService.findAllComuna());
		mv.addObject("findAllEstadoCivil",estadoCivilService.findAllEstadoCivil());
		return mv;
	}
	
	@PostMapping("/ajax/createUser")
	public @ResponseBody Boolean createUser(@RequestBody UserEntity user){
		return userClient.registerNewUser(user);
	}

}
