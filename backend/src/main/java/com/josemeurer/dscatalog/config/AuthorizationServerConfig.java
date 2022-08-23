package com.josemeurer.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer //Fala pro sistema que essa classe é o authorization server
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private JwtTokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //https://www.linkedin.com/pulse/spring-boot-oauth2-securing-rest-api-abid-anjum/
        //https://docs.spring.io/spring-security/oauth/apidocs/org/springframework/security/oauth2/config/annotation/web/configuration/AuthorizationServerConfigurerAdapter.html
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override //Credenciais da aplicacao
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() //Processo vai ser feito em memoria
                .withClient("dscatalog") //Id da aplicacao, id da solicitacao do front precisa dar match com essa
                .secret(bCrypt.encode("ç123")) //Senha da aplicacao
                .scopes("read", "write") //Permissoes de acesso
                .authorizedGrantTypes("password") //Padrao do Oauth
                .accessTokenValiditySeconds(86400); //Tempo valido do token em segundos
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) //Criado no WebSecurityConfig
                .tokenStore(tokenStore) //Criado no AppConfig
                .accessTokenConverter(accessTokenConverter);
    }
}
