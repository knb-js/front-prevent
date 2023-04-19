package com.front.prev.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.front.prev.client.UserClient;
import com.front.prev.dto.UserDTO;
import com.front.prev.dto.tables.ResultTableUser;
import com.front.prev.entity.UserEntity;
import com.front.prev.util.PrevUtil;
import com.front.prev.util.ViewConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class UserController {
	
	@Autowired
	private UserClient userClient;
	
	@GetMapping("/adm-user")
	public ModelAndView showUser(HttpSession session,@CookieValue(value = "localeInfo", defaultValue = "false") String locateCookie) {
		ModelAndView mv = new ModelAndView(ViewConstants.USER_ADM);
		UserEntity user = PrevUtil.getUser();
		mv.addObject("titulo", "Usuarios");
		mv.addObject("locateCookie", locateCookie);
		mv.addObject("user", user);
		return mv;
	}
	
	@GetMapping("/ajax/listUser/{rut}/{name}/{lastName}/{id_profile}/{email}")
	public @ResponseBody ResultTableUser ajaxListUser(
			@PathVariable("rut") String rut,
			@PathVariable("name") String name,
			@PathVariable("lastName") String lastName,
			@PathVariable("id_profile") String id_profile,
			@PathVariable("email") String email,
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
			@RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
			@RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
			@RequestParam(value = "search[value]", required = false) String search, HttpSession session, Model modelo)
			throws IOException {
		Page<UserDTO> listUser = null;
		ResultTableUser dtbListTrxPos = new ResultTableUser();
		Integer pageNo = (start) / length + 1;
		
		try {
			listUser = findAllPage(pageNo,length,rut,name,lastName,id_profile,email);
			dtbListTrxPos.setData(listUser);
			dtbListTrxPos.setDraw(draw);
			dtbListTrxPos.setLength(length);
			dtbListTrxPos.setRecordsTotal(listUser.getTotal());
			dtbListTrxPos.setRecordsFiltered(listUser.getTotal());
			return dtbListTrxPos;
		} catch (Exception e) {
			listUser = new Page<UserDTO>();
			dtbListTrxPos.setData(listUser);
			dtbListTrxPos.setDraw(draw);
			dtbListTrxPos.setLength(length);
			dtbListTrxPos.setRecordsTotal(listUser.getTotal());
			dtbListTrxPos.setRecordsFiltered(listUser.getTotal());
			return dtbListTrxPos;
		}
	}
	
	private Page<UserDTO> findAllPage(Integer pageNo,Integer length,String rut,String name,String lastName,String id_profile,String email) {
		PageHelper.startPage(pageNo, length);
		return userClient.findAllPage(rut,name,lastName,id_profile,email);
	}
	
	//@PostMapping("/ajax/createUser")
	//public @ResponseBody Boolean createUser(@RequestBody UserDTO user){
	//	return userClient.registerNewUser(user);
	//}
	
	@PutMapping("/ajax/updateUser/{idUser}/{field}/{value}")
	public @ResponseBody Boolean updateUser(@PathVariable("idUser")Integer idUser,@PathVariable("field") String field,@PathVariable("value") String value) {
		return userClient.updateUser(idUser, field, value);
	}
	

}
