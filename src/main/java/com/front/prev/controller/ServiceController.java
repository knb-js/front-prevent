package com.front.prev.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.front.prev.client.ServicioClient;
import com.front.prev.dto.ServicioDTO;
import com.front.prev.dto.tables.ResultTableService;
import com.front.prev.entity.UserEntity;
import com.front.prev.util.PrevUtil;
import com.front.prev.util.ViewConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class ServiceController {
	
	@Autowired
	private ServicioClient servicioClient;
	
	@GetMapping("/adm-service")
	public ModelAndView showService(HttpSession session,@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie) {
		ModelAndView mv = new ModelAndView(ViewConstants.SERVICE_ADM);
		UserEntity user = PrevUtil.getUser();
		mv.addObject("titulo", "Servicios");
		mv.addObject("locateCookie", locateCookie);
		mv.addObject("user", user);
		return mv;
	}
	
	@GetMapping("/ajax/listService/{nombre}/{descripcion}")
	public @ResponseBody ResultTableService ajaxListService(
			@PathVariable("nombre") String nombre,
			@PathVariable("descripcion") String descripcion,
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
			@RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
			@RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
			@RequestParam(value = "search[value]", required = false) String search, HttpSession session, Model modelo)
			throws IOException {
		Page<ServicioDTO> listService = null;
		ResultTableService dtbListTrxPos = new ResultTableService();
		Integer pageNo = (start) / length + 1;
		
		try {
			listService = findAllPage(pageNo,length,nombre,descripcion);
			dtbListTrxPos.setData(listService);
			dtbListTrxPos.setDraw(draw);
			dtbListTrxPos.setLength(length);
			dtbListTrxPos.setRecordsTotal(listService.getTotal());
			dtbListTrxPos.setRecordsFiltered(listService.getTotal());
			return dtbListTrxPos;
		} catch (Exception e) {
			listService = new Page<ServicioDTO>();
			dtbListTrxPos.setData(listService);
			dtbListTrxPos.setDraw(draw);
			dtbListTrxPos.setLength(length);
			dtbListTrxPos.setRecordsTotal(listService.getTotal());
			dtbListTrxPos.setRecordsFiltered(listService.getTotal());
			return dtbListTrxPos;
		}
	}
	
	private Page<ServicioDTO> findAllPage(Integer pageNo,Integer length,String nombre,String descripcion) {
		PageHelper.startPage(pageNo, length);
		return servicioClient.findAllPage(nombre,descripcion);
	}
	
	@PostMapping("/ajax/createService")
	public @ResponseBody Boolean createService(@RequestBody ServicioDTO servicio){
		return servicioClient.insertServicio(servicio);
	}
	
	@PutMapping("/ajax/updateService/{idService}/{field}/{value}")
	public @ResponseBody Boolean updateServiceByParams(@PathVariable("idService") Integer idService,@PathVariable("field") String field,@PathVariable("value") String value){
		return servicioClient.updateServicioByParams(idService, field, value);
	}

}
