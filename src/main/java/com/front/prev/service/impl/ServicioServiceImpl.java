package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.ServicioClient;
import com.front.prev.dto.ServicioDTO;
import com.front.prev.service.ServicioService;

@Service
public class ServicioServiceImpl implements ServicioService {
	
	@Autowired
	private ServicioClient servicioClient;

	@Override
	public List<ServicioDTO> allService() {
		return servicioClient.findAll();
	}

	@Override
	public ServicioDTO findById(Integer idServicio) {
		return servicioClient.findById(idServicio);
	}

}
