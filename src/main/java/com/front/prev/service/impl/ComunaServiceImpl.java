package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.ComunaClient;
import com.front.prev.dto.ComunaDTO;
import com.front.prev.service.ComunaService;

@Service
public class ComunaServiceImpl implements ComunaService{

	@Autowired
	private ComunaClient comunaClient;
	
	@Override
	public List<ComunaDTO> findAllComuna() {
		return comunaClient.findAllComuna();
	}

}
