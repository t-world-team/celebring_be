package com.tworld.celebring.celeb.service;

import com.tworld.celebring.celeb.dto.CelebDto;
import com.tworld.celebring.celeb.model.Celeb;
import com.tworld.celebring.celeb.repository.CelebRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CelebService {
    @Autowired
    private final CelebRepository celebRepository;

    public List<CelebDto> getCelebList() {
        List<Celeb> celebList = celebRepository.findAllByDeleteEntityDeleteYn("N");
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> CelebDto.builder().celeb(celeb).build()).collect(Collectors.toList());
        return celebDtoList;
    }

    public List<CelebDto> getGroupCelebListGroupingByConsonant(String startConsonant, String endConsonant) {
        List<Celeb> celebList = celebRepository.findGroupCelebByConsonant("N", startConsonant, endConsonant);
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> CelebDto.builder().celeb(celeb).build()).collect(Collectors.toList());
        return celebDtoList;
    }
    public List<CelebDto> getSoloCelebListGroupingByConsonant(String startConsonant, String endConsonant) {
        List<Celeb> celebList = celebRepository.findSoloCelebByConsonant("N", startConsonant, endConsonant);
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> CelebDto.builder().celeb(celeb).build()).collect(Collectors.toList());
        return celebDtoList;
    }
}
