package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.GeneroClient;
import com.front.prev.dto.GeneroDTO;
import com.front.prev.service.GeneroService;

@Service
public class GeneroServiceImpl implements GeneroService{

	@Autowired
	private GeneroClient generoClient;
	
	
	@Override
	public List<GeneroDTO> findAllGenero() {
		return generoClient.findAllGenero();
	}

	
}
