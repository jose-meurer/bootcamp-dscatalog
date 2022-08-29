package com.josemeurer.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer //Esta classe implementa o resource server do Oauth2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;

    @Autowired
    private Environment env;

    private static final String[] PUBLIC = {"/oauth/token", "/h2-console/**" };

    private static final String[] OPERATOR_OR_ADMIN = {"/products/**", "/categories/**" };

    private static final String[] ADMIN = {"/users/**" };


    @Override   //Decodifica o token e verifica se o token é valido
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //Se o app.properties rodando for de teste, ele libera o frame do h2
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll() //Define as autorizaçoes
                .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll() //Liberar o metodo get para todos
                .antMatchers(OPERATOR_OR_ADMIN).hasAnyAuthority("OPERATOR", "ADMIN") //Pode acessar quem tiver roles
                .antMatchers(ADMIN).hasRole("ADMIN") //Só pode acessar quem tiver role admin
                .anyRequest().authenticated(); //Para acessar qualquer outra só precisa estar logado, nao imporanto o perfil de user
    }
}
