package com.front.prev.service;

import java.util.List;

import com.front.prev.dto.ServicioDTO;

public interface ServicioService {

	List<ServicioDTO> allService();
	
	ServicioDTO findById(Integer idServicio);
	
}


