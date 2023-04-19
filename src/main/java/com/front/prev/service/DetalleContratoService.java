package com.front.prev.service;

import java.util.List;

import com.front.prev.dto.DetalleContratoDTO;

public interface DetalleContratoService {

	public List<DetalleContratoDTO> findAllDetalleContrato();
	
	public DetalleContratoDTO findByIdDetalleContrato(Integer idDetalleContrato);
	
	public Boolean insertServicio(DetalleContratoDTO detalleContrato);
	
}
