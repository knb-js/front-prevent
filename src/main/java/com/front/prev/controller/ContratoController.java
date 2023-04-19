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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.front.prev.client.ContratoClient;
import com.front.prev.client.PagoClient;
import com.front.prev.dto.ContratoDTO;
import com.front.prev.dto.tables.ResultTableContrato;
import com.front.prev.entity.UserEntity;
import com.front.prev.util.PrevUtil;
import com.front.prev.util.ViewConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class ContratoController {
	
	@Autowired
	private ContratoClient contratoClient;
	
	@Autowired
	private PagoClient pagoClient;
	
	@GetMapping("/adm-contrato")
	public ModelAndView showContrato(HttpSession session,@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie) {
		ModelAndView mv = new ModelAndView(ViewConstants.CONTRATO_ADM);
		UserEntity user = PrevUtil.getUser();
		mv.addObject("titulo", "Contratos");
		mv.addObject("locateCookie", locateCookie);
		mv.addObject("user", user);
		return mv;
	}
	
	@GetMapping("/ajax/listContrato/{fechaContratacion}/{fechaTermino}")
	public @ResponseBody ResultTableContrato ajaxListContrato(
			@PathVariable("fechaContratacion") String fechaContratacion,
			@PathVariable("fechaTermino") String fechaTermino,
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
			@RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
			@RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
			@RequestParam(value = "search[value]", required = false) String search, HttpSession session, Model modelo)
			throws IOException {
		Page<ContratoDTO> listContrato = null;
		ResultTableContrato dtbListTrxPos = new ResultTableContrato();
		Integer pageNo = (start) / length + 1;
		UserEntity user = PrevUtil.getUser();
		try {
			listContrato = findAllPageContrato(pageNo,length, fechaContratacion, fechaTermino, user.getIdUser()+"");
			dtbListTrxPos.setData(listContrato);
			dtbListTrxPos.setDraw(draw);
			dtbListTrxPos.setLength(length);
			dtbListTrxPos.setRecordsTotal(listContrato.getTotal());
			dtbListTrxPos.setRecordsFiltered(listContrato.getTotal());
			return dtbListTrxPos;
		} catch (Exception e) {
			listContrato = new Page<ContratoDTO>();
			dtbListTrxPos.setData(listContrato);
			dtbListTrxPos.setDraw(draw);
			dtbListTrxPos.setLength(length);
			dtbListTrxPos.setRecordsTotal(listContrato.getTotal());
			dtbListTrxPos.setRecordsFiltered(listContrato.getTotal());
			return dtbListTrxPos;
		}
	}
	
	private Page<ContratoDTO> findAllPageContrato(Integer pageNo,Integer length,String fechaContratacion,String fechaTermino, String idUser) {
		PageHelper.startPage(pageNo, length);
		return contratoClient.findAllPageContrato(fechaContratacion, fechaTermino, idUser);
	}
	
	@PutMapping("/ajax/updateContrato/{idContrato}/{field}/{value}")
	public @ResponseBody Boolean updateContrato(@PathVariable("idContrato")Integer idContrato,@PathVariable("field") String field,@PathVariable("value") String value) {
		return contratoClient.updateContrato(idContrato, field, value);
	}
	
	@PostMapping("/ajax/generatePago/{idContrato}/{monto}")
	public @ResponseBody Boolean generatePago(@PathVariable("idContrato")Integer idContrato,@PathVariable("monto") Integer monto) {
		UserEntity user = PrevUtil.getUser();
		return pagoClient.generatePaymentContrato(idContrato, monto, user.getIdUser());
	}
	
}	
