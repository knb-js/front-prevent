package com.front.prev.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.prev.config.ConfigClientValues;
import com.front.prev.dto.DetalleContratoDTO;
import com.front.prev.dto.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@EnableAsync
public class DetalleContratoClient {
	
private final RestTemplate rest = new RestTemplate();
	
	@Autowired
	private ConfigClientValues configClientValues;
	
	ObjectMapper om = new ObjectMapper();
	
	
	public List<DetalleContratoDTO> findAllDetalleContrato() {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/detalleContrato", ResponseDTO.class);
			return Arrays.asList(om.readValue(om.writeValueAsBytes(result.getBody().getData()), DetalleContratoDTO[].class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public DetalleContratoDTO findByIdDetalleContrato(Integer idDetalleContrato) {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/detalleContrato/getByIdDetalleContrato/"+idDetalleContrato, ResponseDTO.class);
			return om.readValue(om.writeValueAsBytes(result.getBody().getData()), DetalleContratoDTO.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean insertServicio(DetalleContratoDTO detalleContrato) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<DetalleContratoDTO> requestEntity = new HttpEntity<DetalleContratoDTO>(detalleContrato, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/detalleContrato/insertDetalleContrato", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	private List<ClientHttpRequestInterceptor> getInterceptos (){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add( new BasicAuthenticationInterceptor( configClientValues.getUserApi(), configClientValues.getPasswordApi() ) );
        return interceptors;
	}
}
