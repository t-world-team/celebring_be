package com.tworld.celebring.user.dto;

import com.tworld.celebring.user.model.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String oauthId;

    public UserDto(Long id, String oauthId) {
        this.id = id;
        this.oauthId = oauthId;
    }

    public User toEntity() {
        return User.builder().oauthId(oauthId).build();
    }
}
