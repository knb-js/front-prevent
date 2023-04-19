package com.front.prev.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserEntity implements UserDetails {
	
	private static final long serialVersionUID = -6749654031045437690L;
	private Integer idUser;
	private String login;
	private String address;
	private String startDate;
	private String birthdayStr;
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
	private ProfileEntity profile;
	private NacionalidadEntity nacionalidad;
	private GeneroEntity genero;
	private ComunaEntity comuna;
	private EstadoCivilEntity estadoCivil;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		try {
			return new ArrayList<GrantedAuthority>(auths);
		} catch (Exception e) {
			auths.add(new SimpleGrantedAuthority("ADMINISTRADOR"));
		}
		return new ArrayList<GrantedAuthority>(auths);
	}
	
	@Override
	public String getPassword() {
		return this.passwd;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "UserEntity [idUser=" + idUser + ", login=" + login + ", address=" + address + ", startDate=" + startDate
				+ ", birthdayStr=" + birthdayStr + ", birthday=" + birthday + ", username=" + username + ", rut=" + rut
				+ ", dv=" + dv + ", name=" + name + ", lastName=" + lastName + ", contactPhone=" + contactPhone
				+ ", email=" + email + ", corporateEmail=" + corporateEmail + ", image=" + image + ", passwd=" + passwd
				+ ", lastConnection=" + lastConnection + ", firstSession=" + firstSession + ", verified=" + verified
				+ ", active=" + active + ", created=" + created + ", updated=" + updated + ", profile=" + profile
				+ ", nacionalidad=" + nacionalidad + ", genero=" + genero + ", comuna=" + comuna + ", estadoCivil="
				+ estadoCivil + "]";
	}
	
}
