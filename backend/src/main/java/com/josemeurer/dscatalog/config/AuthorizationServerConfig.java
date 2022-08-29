package com.josemeurer.dscatalog.config;

import com.josemeurer.dscatalog.components.JwtTokenEnhancer;
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
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

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

    @Autowired
    private JwtTokenEnhancer tokenEnhancer;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception { //Oauth2

        /* O Spring security oauth expõe dois endpoints para verificação de tokens
         * ( /oauth/check_token e /oauth/token_key) que são protegidos por padrão por trás do denyAll().
         * Os métodos tokenKeyAccess() e checkTokenAccess() abrem esses terminais para uso.
         * https://howtodoinjava.com/spring-boot2/oauth2-auth-server/
        */

        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
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

        TokenEnhancerChain chain = new TokenEnhancerChain();

        //Dados adicionais inseridos na classe JwtTokenEnhancer, método enhancer
        chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));


        endpoints.authenticationManager(authenticationManager) //SpringSecurity que vai fazer a autenticacao
                .tokenStore(tokenStore) //Responsavel por processar o token
                .accessTokenConverter(accessTokenConverter) //Responsavel para converter o tokenos
                .tokenEnhancer(chain);
    }
}
