package dev.josemeurer.jwt.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("jose")
                        .password("{noop}123456") //NoOpPasswordEncoder, codificador de senha que não faz nada e é útil para teste
                        .authorities("read")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable() //Desativar falsificação de solicitação entre sites (CSRF)
                .authorizeRequests(auth -> auth
                        .anyRequest().authenticated() //O usuário deve ser autenticado para qualquer solicitação no aplicativo
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) //OAuth2ResourceServerConfigurer é um AbstractHttpConfigurer suporte para servidor de recursos OAuth 2.0
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //O Spring Security nunca criará um HttpSession e nunca o usará para obter o Security Context
                .httpBasic(Customizer.withDefaults()) //O suporte de autenticação básica HTTP do Spring Security é ativado por padrão. No entanto, assim que qualquer configuração baseada em servlet for fornecida, o HTTP Basic deverá ser fornecido explicitamente
                .build();
        //Nunca desative a proteção CSRF deixando o gerenciamento de sessão ativado! Fazer isso abrirá você para um ataque de falsificação de solicitação entre sites

        //Ao usar o personalizador JWT, você precisa fornecer um dos seguintes:
        //Forneça um Jwk Set Uri viaOAuth2ResourceServerConfigurer.JwtConfigurer.jwkSetUri
        //Forneça uma instância JwtDecoder viaOAuth2ResourceServerConfigurer.JwtConfigurer.decoder
        //Exponha um bean JwtDecoder.
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
