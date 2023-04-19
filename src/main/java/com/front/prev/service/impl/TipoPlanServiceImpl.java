package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.TipoPlanClient;
import com.front.prev.dto.TipoPlanDTO;
import com.front.prev.service.TipoPlanService;

@Service
public class TipoPlanServiceImpl implements TipoPlanService{

	@Autowired
	private TipoPlanClient tipoPlanClient;
	
	@Override
	public List<TipoPlanDTO> findAllTipoPlan() {
		return tipoPlanClient.findAllTipoPlan();
	}
}
