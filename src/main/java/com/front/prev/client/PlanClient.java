package com.front.prev.client;

import java.util.ArrayList;
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

import com.front.prev.config.ConfigClientValues;
import com.front.prev.dto.PlanDTO;
import com.front.prev.dto.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@EnableAsync
public class PlanClient {

private final RestTemplate rest = new RestTemplate();
	
	@Autowired
	private ConfigClientValues configClientValues;
	
	public Integer findByIdTipoPlan(Integer idTipoPlan) {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/Planes/findByIdTipoPlan/"+idTipoPlan, ResponseDTO.class);
			return (Integer) result.getBody().getData();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public void updateTipoPlan(PlanDTO tipoPlan) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<PlanDTO> requestEntity = new HttpEntity<PlanDTO>(tipoPlan, headers);
	        rest.exchange(configClientValues.getUrlBackend() + "/servicios/update", HttpMethod.PUT, requestEntity,ResponseDTO.class);
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
		}
	}
	
	public void insertTipoPlan(PlanDTO tipoPlan) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<PlanDTO> requestEntity = new HttpEntity<PlanDTO>(tipoPlan, headers);
	        rest.exchange(configClientValues.getUrlBackend() + "/servicios/insert", HttpMethod.POST, requestEntity,ResponseDTO.class);
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
		}
	}
	
		
	private List<ClientHttpRequestInterceptor> getInterceptos (){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add( new BasicAuthenticationInterceptor( configClientValues.getUserApi(), configClientValues.getPasswordApi() ) );
        return interceptors;
	}
}
