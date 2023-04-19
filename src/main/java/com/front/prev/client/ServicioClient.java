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
import com.front.prev.dto.ResponseDTO;
import com.front.prev.dto.ResponsePageHelper;
import com.front.prev.dto.ServicioDTO;
import com.github.pagehelper.Page;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@EnableAsync
public class ServicioClient {

	private final RestTemplate rest = new RestTemplate();
	
	@Autowired
	private ConfigClientValues configClientValues;
	
	ObjectMapper om = new ObjectMapper();
	
	
	public List<ServicioDTO> findAll() {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/servicios", ResponseDTO.class);
			return Arrays.asList(om.readValue(om.writeValueAsBytes(result.getBody().getData()), ServicioDTO[].class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public Page<ServicioDTO> findAllPage(String nombre,String descripcion) {
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/servicios/page/"+nombre+"/"+descripcion, ResponseDTO.class);
			return om.readValue(om.writeValueAsBytes(result.getBody().getData()), ResponsePageHelper.class).getPage();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ServicioDTO findById(Integer idServicio) {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/servicios/getById/"+idServicio, ResponseDTO.class);
			return om.readValue(om.writeValueAsBytes(result.getBody().getData()), ServicioDTO.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void updateServicio(ServicioDTO servicio) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<ServicioDTO> requestEntity = new HttpEntity<ServicioDTO>(servicio, headers);
	        rest.exchange(configClientValues.getUrlBackend() + "/servicios/update", HttpMethod.PUT, requestEntity,ResponseDTO.class);
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
		}
	}
	
	public Boolean updateServicioByParams(Integer idService, String field, String value) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<ServicioDTO> requestEntity = new HttpEntity<ServicioDTO>(null, headers);
	        return rest.exchange(configClientValues.getUrlBackend() + "/servicios/update/"+idService+"/"+field+"/"+value, HttpMethod.PUT, requestEntity,ResponseDTO.class).getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	public Boolean insertServicio(ServicioDTO servicio) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<ServicioDTO> requestEntity = new HttpEntity<ServicioDTO>(servicio, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/servicios/insert", HttpMethod.POST, requestEntity,ResponseDTO.class);
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
