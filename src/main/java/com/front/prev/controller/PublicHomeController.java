package com.front.prev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.front.prev.client.PagoClient;
import com.front.prev.dto.PaymentDTO;
import com.front.prev.dto.ServicioDTO;
import com.front.prev.entity.UserEntity;
import com.front.prev.service.ServicioService;
import com.front.prev.util.PrevUtil;
import com.front.prev.util.ViewConstants;

@Controller
public class PublicHomeController {
	
	@Autowired
	private ServicioService servicioService;
	
	@Autowired
	private PagoClient pagoClient;
	
	

	@GetMapping("/public-home")
		private ModelAndView indexPrincipal(Model modelo) {
			ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_HOME);
			UserEntity user = PrevUtil.getUser();
			mv.addObject("listAllService",servicioService.allService());
			mv.addObject("user",user);
			return mv;
		}
	
	@GetMapping("/about-us")
		private ModelAndView aboutUs(Model modelo) {
		ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_ABOUT_US);
		UserEntity user = PrevUtil.getUser();
		
		mv.addObject("user",user);
		return mv;
	}
	
	@GetMapping("/contact")
	private ModelAndView contact(Model modelo) {
		ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_CONTACT);
		UserEntity user = PrevUtil.getUser();
		
		mv.addObject("user",user);
		return mv;
	}
	
	@GetMapping("/service")
	private ModelAndView service(Model modelo) {
		ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_SERVICE);
		UserEntity user = PrevUtil.getUser();
		
		mv.addObject("user",user);
		mv.addObject("listAllService",servicioService.allService());
		return mv;
	}
	
	@GetMapping("/shopping-cart")
	private ModelAndView shoppingCart(Model modelo) {
		ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_SHOPPING_CART);
//		mv.addObject("servicio",servicioService.findById(idService));
		
		UserEntity user = PrevUtil.getUser();
		
		mv.addObject("user",user);
		return mv;
	}
	
	@GetMapping("/service/{idService}")
	private ModelAndView getService(@PathVariable("idService") Integer idService, Model modelo) {
		ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_SERVICE_BY_ID);
		UserEntity user = PrevUtil.getUser();
		
		mv.addObject("user",user);
		mv.addObject("servicio",servicioService.findById(idService));
		return mv;
	}
	
	@GetMapping("/pay-service")
	private ModelAndView payService(Model modelo) {
		
		ModelAndView mv = new ModelAndView(ViewConstants.PUBLIC_PAY);
		
		UserEntity user = PrevUtil.getUser();
		
		mv.addObject("user",user);
		return mv;
	}
	
	@PostMapping("/ajax/payment")
	public @ResponseBody Boolean payment(@RequestBody List<ServicioDTO> services) {
		UserEntity user = PrevUtil.getUser();
		
		
		return pagoClient.generatePayment(PaymentDTO.builder().user(user).servicios(services).build());

		
		//CREAR UNA API PARA INSERTAR EN TABLA CONTRATO -> CONEXION CON FRONTEND IGUAL
		
//		Boolean insertContrato = contratoClient.insertContrato(ContratoDTO.builder().build());
		
		//CREAR TABLA DETALLE CONTRATO CON LOS CAMPOS -> ID_DETALLE_CONTRATO, ID_CONTRATO, ID_SERVICIO,VALOR 
		//CREAR API PARA INSERTAR EN TABLA DETALLE_CONTRATO -> CONEXION CON FRONTEND IGUAL
		//CREAR API PARA INSERTAR EN TABLA PAGO -> CONEXION CON FRONTEND IGUAL
		
		//TE AYUDA SEBA POR SUSHI
		
		//UTILIZAR API PARA INSERCCIÓN DE CONTRATO
		//OBTENER DATOS DE LOS SERVICIOS A PAGAR
		//USAR EN API DE TABLA DETALLE_CONTRATO PARA INSERTAR
		//USAR API PARA INSERTAR PAGO.
		
		//UNA VEZ TERMINIADO TODO ENCACILLAR TODO EN UN TRY CATCH PARA VALIDAR QUE TODO FUNCIONO BIEN Y RETORNAR UN TRUE, EN CASO CONTRARIO UN FALSE
	}
}
