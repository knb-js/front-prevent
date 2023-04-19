package com.front.prev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class UserDTO {
	
	private Integer idUser;
	private String login;
	private String address;
	private String startDate;
	private String birthday;
	private String username;
	private Integer rut;
	private String dv;
	private String name;
	private String lastName;
	private String contactPhone;
	private String email;
	private String corporateEmail;
	private String image;
	private String passwd;
	private String lastConnection;
	private String firstSession;
	private String verified;
	private String active;
	private String created;
	private String updated;
	private ProfileDTO profile;
	private NacionalidadDTO nacionalidad;
	private GeneroDTO genero;
	private ComunaDTO comuna;
	private EstadoCivilDTO estadoCivil;

}
