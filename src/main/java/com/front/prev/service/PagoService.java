package com.front.prev.service;

import java.util.List;

import com.front.prev.dto.PagoDTO;

public interface PagoService {

	public List<PagoDTO> findAllPago();
	
	public PagoDTO findByIdPago(Integer idPago);
	
	public Boolean insertPago(PagoDTO pago);
}
