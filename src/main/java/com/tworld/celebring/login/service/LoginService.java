package com.tworld.celebring.login.service;

import com.tworld.celebring.login.model.LoginUser;
import com.tworld.celebring.user.model.User;
import com.tworld.celebring.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Service
public class LoginService implements OAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthService = new DefaultOAuth2UserService();

        // 서비스 이름 - google, naver, kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 서비스에서 제공하는 id를 가져오는 key값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 각 서비스에서 가져온 유저 정보
        OAuth2User oAuth2User = oauthService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        LoginUser loginUser = new LoginUser(userNameAttributeName, registrationId, attributes);
        User userInfo = getUserInfoByOauthId(loginUser.getOAuthId()).orElse(null);
        if(userInfo == null) {
            // insert
            userInfo = saveUser(LoginUser.convertToUser(loginUser));
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userInfo.getRole())), LoginUser.convertToMap(loginUser), userNameAttributeName
        );
    }

    public Optional<User> getUserInfoByOauthId(String oauthId) {
        return userRepository.findByOauthId(oauthId);
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

}
