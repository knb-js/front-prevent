package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.RubroClient;
import com.front.prev.dto.RubroDTO;
import com.front.prev.service.RubroService;

@Service
public class RubroServiceImpl implements RubroService{

	@Autowired
	private RubroClient rubroClient;
	
	@Override
	public List<RubroDTO> findAllRubro() {	
		return rubroClient.findAllRubro();
	}

}
