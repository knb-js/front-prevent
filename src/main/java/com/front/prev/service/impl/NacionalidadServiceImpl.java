package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.NacionalidadClient;
import com.front.prev.dto.NacionalidadDTO;
import com.front.prev.service.NacionalidadService;

@Service
public class NacionalidadServiceImpl implements NacionalidadService{

	@Autowired
	private NacionalidadClient nacionalidadClient;

	@Override
	public List<NacionalidadDTO> findAllNacionalidad() {
		return nacionalidadClient.findAllNacionalidad();
	}
	
	
}
