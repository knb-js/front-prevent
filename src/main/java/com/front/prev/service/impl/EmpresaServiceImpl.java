package com.front.prev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.front.prev.client.EmpresaClient;
import com.front.prev.dto.EmpresaDTO;
import com.front.prev.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaClient empresaClient;
	
	@Override
	public List<EmpresaDTO> findAllEmpresa() {
		return empresaClient.findAllEmpresa();
	}

}
