package com.josemeurer.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {

	@Bean
	BCryptPasswordEncoder passwordEncoder( ) {
		return new BCryptPasswordEncoder();
	}

	//Poderia colocar os beans do jwt em um pasta só para security
	@Bean //Assinatura do meu token
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("MY-JWT-SECRET"); //Tirar hardcode e por no properties
		return tokenConverter;
	}

	@Bean //Beans necessarios para implementar o authorization server
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
