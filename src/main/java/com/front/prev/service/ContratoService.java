package com.front.prev.service;

import java.util.List;

import com.front.prev.dto.ContratoDTO;

public interface ContratoService {

	public List<ContratoDTO> findAllContrato();
	
	public Boolean insertContrato(ContratoDTO contrato);
	
}
