package com.tworld.celebring.celeb.dto;

import lombok.Data;

import java.util.List;

@Data
public class CelebRequestDto {
    CelebAddDto celeb;
    List<CelebAddDto> members;
}
