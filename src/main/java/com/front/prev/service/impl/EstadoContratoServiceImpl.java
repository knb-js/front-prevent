package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.EstadoContratoClient;
import com.front.prev.dto.EstadoContratoDTO;
import com.front.prev.service.EstadoContratoService;

@Service
public class EstadoContratoServiceImpl implements EstadoContratoService{

	@Autowired
	private EstadoContratoClient estadoContratoClient;
	
	@Override
	public List<EstadoContratoDTO> findAllEstadoContrato() {
		return estadoContratoClient.findAllEstadoContrato();
	}

}
