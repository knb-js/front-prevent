package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.PagoClient;
import com.front.prev.dto.PagoDTO;
import com.front.prev.service.PagoService;

@Service
public class PagoServiceImpl implements PagoService{
	
	@Autowired
	private PagoClient pagoClient;
	
	@Override
	public List<PagoDTO> findAllPago() {
		return pagoClient.findAllPago();
	}

	@Override
	public PagoDTO findByIdPago(Integer idPago) {
		return pagoClient.findByIdPago(idPago);
	}

	@Override
	public Boolean insertPago(PagoDTO pago) {
		return pagoClient.insertPago(pago);
	}

}
