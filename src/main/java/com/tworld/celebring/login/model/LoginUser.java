package com.tworld.celebring.login.model;

import com.tworld.celebring.user.dto.UserDto;
import com.tworld.celebring.user.model.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LoginUser {

    private String userNameAttributeName;
    private String service;
    private String oAuthId;
    private String name;
    private String email;

    public LoginUser(String userNameAttributeName, String service, Map<String, Object> attributes) {
        this.userNameAttributeName = userNameAttributeName;
        this.service = service;

        switch (service) {
            case "google" -> {
                this.oAuthId = attributes.get("sub").toString();
                this.name = attributes.get("name").toString();
                this.email = attributes.get("email").toString();
            }
            case "naver" -> {
                Map<String, Object> profile = (Map<String, Object>) attributes.get("response");
                this.oAuthId = profile.get("id").toString();
                this.name = profile.get("nickname").toString();
                this.email = profile.get("email").toString();
            }
            case "kakao" -> {
                Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                this.oAuthId = attributes.get("id").toString();
                this.name = properties.get("nickname").toString();
                this.email = kakaoAccount.get("email").toString();
            }
        }
    }

    public static Map<String, Object> convertToMap(LoginUser user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(user.getUserNameAttributeName(), user.getOAuthId());
        userMap.put("service", user.getService());
        userMap.put("id", user.getOAuthId());
        userMap.put("name", user.getName());
        userMap.put("email", user.getEmail());
        return userMap;
    }

    public static User convertToUser(LoginUser loginUser) {
        return new UserDto(null, loginUser.getOAuthId(), loginUser.getName(), loginUser.getEmail()).toEntity();
    }

}
