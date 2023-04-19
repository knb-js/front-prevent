package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.front.prev.client.ContratoClient;
import com.front.prev.dto.ContratoDTO;
import com.front.prev.service.ContratoService;

public class ContratoServiceImpl implements ContratoService{

	@Autowired
	private ContratoClient contratoClient;
	
	@Override
	public List<ContratoDTO> findAllContrato() {
		return contratoClient.findAllContrato();
	}

	@Override
	public Boolean insertContrato(ContratoDTO contrato) {
		return contratoClient.insertContrato(contrato);
	}

}
