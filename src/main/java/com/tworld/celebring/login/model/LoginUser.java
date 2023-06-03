package com.tworld.celebring.login.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class LoginUser {

    private String oAuthId;
    private String name;
    private String email;

    public LoginUser(String service, Map<String, Object> attributes) {
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

}
