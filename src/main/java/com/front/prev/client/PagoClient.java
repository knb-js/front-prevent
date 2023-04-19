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
import com.front.prev.dto.PagoDTO;
import com.front.prev.dto.PaymentDTO;
import com.front.prev.dto.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@EnableAsync
public class PagoClient {
	
	private final RestTemplate rest = new RestTemplate();

	@Autowired
	private ConfigClientValues configClientValues;
	
	ObjectMapper om = new ObjectMapper();
	
	public List<PagoDTO> findAllPago() {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/pagos", ResponseDTO.class);
			return Arrays.asList(om.readValue(om.writeValueAsBytes(result.getBody().getData()), PagoDTO[].class));
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public PagoDTO findByIdPago(Integer idPago) {	
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/pagos/getByIdPago/"+idPago, ResponseDTO.class);
			return om.readValue(om.writeValueAsBytes(result.getBody().getData()), PagoDTO.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public Boolean insertPago(PagoDTO pago) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<PagoDTO> requestEntity = new HttpEntity<PagoDTO>(pago, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/pagos/insertPago", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	public Boolean generatePayment(PaymentDTO payment) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<PaymentDTO> requestEntity = new HttpEntity<PaymentDTO>(payment, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/pagos/generatePayment", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	public Boolean generatePaymentContrato(Integer idContrato,Integer monto,Integer idUser) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<PaymentDTO> requestEntity = new HttpEntity<PaymentDTO>(null, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/pagos/generatePayment/"+idContrato+"/"+monto+"/"+idUser, HttpMethod.POST, requestEntity,ResponseDTO.class);
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
