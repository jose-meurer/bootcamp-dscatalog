package com.josemeurer.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter { //Spring Oauth2

    @Value("${security.oauth2.client.client-id}")   //app.properties
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")   //app.properties
    private String clientSecret;

    @Value("${jwt.duration}")   //app.properties
    private Integer jwtDuration;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter; //AppConfig

    @Autowired
    private JwtTokenStore tokenStore; //AppConfig

    @Autowired
    private AuthenticationManager authenticationManager; //WebSecurityConfig

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception { //Oauth2
        //https://www.linkedin.com/pulse/spring-boot-oauth2-securing-rest-api-abid-anjum/
        //https://docs.spring.io/spring-security/oauth/apidocs/org/springframework/security/oauth2/config/annotation/web/configuration/AuthorizationServerConfigurerAdapter.html
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()"); //Duvida aqui
    }

    @Override //Credenciais da aplicacao
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { //Oauth2
        clients.inMemory() //Processo vai ser feito em memoria
                .withClient(clientId) //Id da aplicacao, id da solicitacao do front precisa dar match com essa
                .secret(bCrypt.encode(clientSecret)) //Client secret, senha da aplicacao
                .scopes("read", "write") //Permissoes de acesso
                .authorizedGrantTypes("password") //Padrao do Oauth
                .accessTokenValiditySeconds(jwtDuration); //Tempo valido do token em segundos
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { //Oauth2
        endpoints.authenticationManager(authenticationManager) //SpringSecurity que vai fazer a autenticacao
                .tokenStore(tokenStore) //Responsavel por processar o token
                .accessTokenConverter(accessTokenConverter); //Responsavel para converter o tokenos
    }
}
