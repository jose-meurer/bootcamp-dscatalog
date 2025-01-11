package com.josemeurer.dscatalog.components;

import com.josemeurer.dscatalog.entities.User;
import com.josemeurer.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override //Na hora que for gerar o token, ele vai adicionar os a dados inseridos aqui
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());

        Map<String, Object> map = new HashMap<>();
        map.put("userFirstName", user.getFirstName());
        map.put("userId", user.getId());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map); //Downcasting

        return accessToken;
    }
}
