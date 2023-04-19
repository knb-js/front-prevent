package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.RepresentanteClient;
import com.front.prev.dto.RepresentanteDTO;
import com.front.prev.service.RepresentanteService;

@Service
public class RepresentanteServiceImpl implements RepresentanteService{

	@Autowired
	private RepresentanteClient representanteClient;
	
	@Override
	public List<RepresentanteDTO> findAllRepresentante() {
		return representanteClient.findAllRepresentante();
	}

}
