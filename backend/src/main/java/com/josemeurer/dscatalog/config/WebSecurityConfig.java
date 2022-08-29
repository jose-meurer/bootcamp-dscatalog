package com.josemeurer.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //Spring Security

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override //Spring Security
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);

		//Spring vai saber como buscar o email e como analisar a senha cryptografada
		auth.userDetailsService(userDetailsService).passwordEncoder(bCrypt);
	}

	@Override 	//Faz o security ignorar os endpoints passando pela biblioteca do Spring cloud Oauth;
	public void configure(WebSecurity web) throws Exception { //Spring Security
		web.ignoring().antMatchers("/actuator/**");
	}

	@Override
	@Bean  //Precisa declarar explicitamente para poder usar como bean
	protected AuthenticationManager authenticationManager() throws Exception { //Spring Security
		return super.authenticationManager();
	}
}
