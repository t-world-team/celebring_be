package com.tworld.celebring.user.dto;

import com.tworld.celebring.user.model.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String oauthId;
    private String name;
    private String email;

    public UserDto(Long id, String oauthId, String name, String email) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder().oauthId(oauthId).name(name).email(email).build();
    }
}
