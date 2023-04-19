package com.front.prev.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity	
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired
	private CustomAuthSuccessHandler customAuthSuccessHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		try {
			 auth.authenticationProvider(authProvider);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("ERROR configureGlobal",e); 
		}
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/actuator/**","/getsessions/**","/resetPassword","/ajax/sendReset/**","/change-password/**","/ajax/change-password/**","/registerNewUser/**","/resources/**","/plugins/**","/confirmAccount/**","/login/**","/recovery-pass/**","/register/**","/ajax/createUser/**","/public-home**","/about-us**","/contact**","/service/**","/shopping-cart/**","/service-by-id/**","/pay-service/**").permitAll()
		    .anyRequest().authenticated()
		    .and()
		 .formLogin().loginPage("/login").loginProcessingUrl("/logincheck")
		 .usernameParameter("txtRut").passwordParameter("txtPasswd")
		 .successHandler(customAuthSuccessHandler).permitAll()
		 .and()
		 .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
		 .clearAuthentication(true)
         .invalidateHttpSession(true)
         .deleteCookies("JSESSIONID", "PLAY_SESSION", "NXSESSIONID", "csrfToken", "SESSION")
		 .permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable(); 
		http.headers().cacheControl().disable();
	}

}
