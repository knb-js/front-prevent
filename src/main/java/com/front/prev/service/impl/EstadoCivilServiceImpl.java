package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.EstadoCivilClient;
import com.front.prev.dto.EstadoCivilDTO;
import com.front.prev.service.EstadoCivilService;

@Service
public class EstadoCivilServiceImpl implements EstadoCivilService{

	@Autowired
	private EstadoCivilClient estadoCivilClient;
	
	@Override
	public List<EstadoCivilDTO> findAllEstadoCivil() {
		return estadoCivilClient.findAllEstadoCivil();
	}

}
