package com.front.prev.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.prev.config.ConfigClientValues;
import com.front.prev.dto.NacionalidadDTO;
import com.front.prev.dto.ResponseDTO;


@Component
@EnableAsync
public class NacionalidadClient {

	private final RestTemplate rest = new RestTemplate();
	
	@Autowired
	private ConfigClientValues configClientValues;
	
	ObjectMapper om = new ObjectMapper();
	
	public List<NacionalidadDTO> findAllNacionalidad() {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/nacionalidad", ResponseDTO.class);
			return Arrays.asList(om.readValue(om.writeValueAsBytes(result.getBody().getData()), NacionalidadDTO[].class));
		} catch (Exception e) {
			return null;
		}
	}
	
	private List<ClientHttpRequestInterceptor> getInterceptos (){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add( new BasicAuthenticationInterceptor( configClientValues.getUserApi(), configClientValues.getPasswordApi() ) );
        return interceptors;
	}
}
