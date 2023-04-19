package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.DetalleContratoClient;
import com.front.prev.dto.DetalleContratoDTO;
import com.front.prev.service.DetalleContratoService;

@Service
public class DetalleContratoServiceImpl implements DetalleContratoService{

	@Autowired
	private DetalleContratoClient detalleContratoClient;
	
	@Override
	public List<DetalleContratoDTO> findAllDetalleContrato() {
		return detalleContratoClient.findAllDetalleContrato();
	}

	@Override
	public DetalleContratoDTO findByIdDetalleContrato(Integer idDetalleContrato) {
		return detalleContratoClient.findByIdDetalleContrato(idDetalleContrato);
	}

	@Override
	public Boolean insertServicio(DetalleContratoDTO detalleContrato) {
		return detalleContratoClient.insertServicio(detalleContrato);
	}

}
