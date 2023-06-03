package com.tworld.celebring.login.service;

import com.tworld.celebring.login.model.LoginUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class LoginService implements OAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oauthService = new DefaultOAuth2UserService();

        // 서비스 이름 - google, naver, kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 서비스에서 제공하는 id를 가져오는 key값
        String idAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 각 서비스에서 가져온 유저 정보
        OAuth2User oAuth2User = oauthService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        LoginUser loginUser = new LoginUser(registrationId, attributes);
        // to-do : login


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, idAttributeName
        );
    }
}
