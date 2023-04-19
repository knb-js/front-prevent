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
import com.front.prev.dto.ContratoDTO;
import com.front.prev.dto.ResponseDTO;
import com.front.prev.dto.ResponsePageHelperContrato;
import com.front.prev.dto.UserDTO;
import com.github.pagehelper.Page;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@EnableAsync
public class ContratoClient {

	private final RestTemplate rest = new RestTemplate();

	@Autowired
	private ConfigClientValues configClientValues;
	
	ObjectMapper om = new ObjectMapper();
	
	public List<ContratoDTO> findAllContrato() {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/contrato", ResponseDTO.class);
			return Arrays.asList(om.readValue(om.writeValueAsBytes(result.getBody().getData()), ContratoDTO[].class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public Page<ContratoDTO> findAllPageContrato(String fechaContratacion, String fechaTermino, String idUser) {
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String url = configClientValues.getUrlBackend() + "/contrato/pageContrato/"+fechaContratacion+"/"+fechaTermino+"/"+idUser;
			System.out.println(url);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(url, ResponseDTO.class);
			return om.readValue(om.writeValueAsBytes(result.getBody().getData()), ResponsePageHelperContrato.class).getPage();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean insertContrato(ContratoDTO contrato) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<ContratoDTO> requestEntity = new HttpEntity<ContratoDTO>(contrato, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/contrato/insertContrato", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	public Boolean updateContrato(Integer idContrato, String field, String value) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(null, headers);
	        return rest.exchange(configClientValues.getUrlBackend() + "/contrato/updateContrato/"+idContrato+"/"+field+"/"+value, HttpMethod.PUT, requestEntity,ResponseDTO.class).getBody().getStatus();
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
