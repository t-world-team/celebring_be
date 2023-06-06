package com.tworld.celebring.login.model;

import com.tworld.celebring.user.dto.UserDto;
import com.tworld.celebring.user.model.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LoginUser {

    private final String userNameAttributeName;
    private final String service;
    private String oAuthId;

    public LoginUser(String userNameAttributeName, String service, Map<String, Object> attributes) {
        this.userNameAttributeName = userNameAttributeName;
        this.service = service;

        switch (service) {
            case "google" -> {
                this.oAuthId = attributes.get("sub").toString();
            }
            case "naver" -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> profile = (Map<String, Object>) attributes.get("response");
                this.oAuthId = profile.get("id").toString();
            }
            case "kakao" -> {
                this.oAuthId = attributes.get("id").toString();
            }
        }
    }

    public static Map<String, Object> convertToMap(LoginUser user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(user.getUserNameAttributeName(), user.getOAuthId());
        userMap.put("service", user.getService());
        userMap.put("id", user.getOAuthId());
        return userMap;
    }

    public static User convertToUser(LoginUser loginUser) {
        return new UserDto(null, loginUser.getOAuthId()).toEntity();
    }

}
