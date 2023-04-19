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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.prev.config.ConfigClientValues;
import com.front.prev.dto.EmailDTO;
import com.front.prev.dto.ResponseDTO;
import com.front.prev.dto.ResponsePageHelperUser;
import com.front.prev.dto.UserDTO;
import com.front.prev.entity.UserEntity;
import com.github.pagehelper.Page;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@EnableAsync
public class UserClient {

	private final RestTemplate rest = new RestTemplate();
	
	@Autowired
	private ConfigClientValues configClientValues;
	
	ObjectMapper om = new ObjectMapper();
	
	public ResponseDTO getUserLogin(String login, String passwd) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<UserEntity> requestEntity = new HttpEntity<UserEntity>(UserEntity.builder().login(login).passwd(passwd).build(), headers);
	        ResponseEntity<ResponseDTO> result = rest.exchange(configClientValues.getUrlBackend() + "/user/login", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Page<UserDTO> findAllPage(String rut,String name, String lastName, String id_profile, String email) {
		try {
			rest.setInterceptors( getInterceptos() );
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String url = configClientValues.getUrlBackend() + "/user/pageUser/"+rut+"/"+name+"/"+lastName+"/"+id_profile+"/"+email;
			ResponseEntity<ResponseDTO> result = rest.getForEntity(url, ResponseDTO.class);
			return om.readValue(om.writeValueAsBytes(result.getBody().getData()),  ResponsePageHelperUser.class).getPage();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getEmailById(Integer idUser) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/user/getMailUser/"+idUser, ResponseDTO.class);
	        return (String) result.getBody().getData();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Async
	public void sendEmailVerifed(EmailDTO email) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<EmailDTO> requestEntity = new HttpEntity<EmailDTO>(email, headers);
	        rest.exchange(configClientValues.getUrlBackend() + "/user/verifedMail", HttpMethod.POST, requestEntity,ResponseDTO.class);
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
		}
	}
	
	public Integer updateVerified(Integer idUser) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        ResponseEntity<ResponseDTO> result = rest.getForEntity(configClientValues.getUrlBackend() + "/user/updateVerified/"+idUser, ResponseDTO.class);
	        return (Integer) result.getBody().getData();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return 0;
		}
	}
	
	public void sendEmailResetPassword(EmailDTO email) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<EmailDTO> requestEntity = new HttpEntity<EmailDTO>(email, headers);
	        rest.exchange(configClientValues.getUrlBackend() + "/user/resetPasswordEmail", HttpMethod.POST, requestEntity,ResponseDTO.class);
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
		}
	}
	
	public Boolean changePassword(UserEntity user) {
		try {
	        rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<UserEntity> requestEntity = new HttpEntity<UserEntity>(user, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/user/updatePassword", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	public Boolean registerNewUser(UserEntity user) {
		try {
	        rest.setInterceptors( getInterceptos());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<UserEntity> requestEntity = new HttpEntity<UserEntity>(user, headers);
	        ResponseEntity<ResponseDTO> result =  rest.exchange(configClientValues.getUrlBackend() + "/user/register", HttpMethod.POST, requestEntity,ResponseDTO.class);
	        return result.getBody().getStatus();
		} catch (Exception e) {
			log.error("Error: ",e.getMessage());
			return false;
		}
	}
	
	public Boolean updateUser(Integer idUser, String field, String value) {
		try {
			rest.setInterceptors( getInterceptos() );
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(null, headers);
	        return rest.exchange(configClientValues.getUrlBackend() + "/user/updateUser/"+idUser+"/"+field+"/"+value, HttpMethod.PUT, requestEntity,ResponseDTO.class).getBody().getStatus();
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
