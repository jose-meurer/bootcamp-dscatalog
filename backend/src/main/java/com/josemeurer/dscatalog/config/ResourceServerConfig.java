package com.josemeurer.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer //Esta classe implementa o resource server do Oauth2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;

    private static final String[] PUBLIC = {"/oauth/token" };

    private static final String[] OPERATOR_OR_ADMIN = {"/products/**", "/categories/**" };

    private static final String[] ADMIN = {"/users/**" };


    @Override   //Decodifica o token e verifica se o token é valido
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll() //Define as autorizaçoes
                .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll() //Liberar o metodo get para todos
                .antMatchers(OPERATOR_OR_ADMIN).hasAnyAuthority("OPERATOR", "ADMIN") //Pode acessar quem tiver roles
                .antMatchers(ADMIN).hasRole("ADMIN") //Só pode acessar quem tiver role admin
                .anyRequest().authenticated(); //Para acessar qualquer outra só precisa estar logado, nao imporanto o perfil de user
    }
}
