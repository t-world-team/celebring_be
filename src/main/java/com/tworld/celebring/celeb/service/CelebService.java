package com.tworld.celebring.celeb.service;

import com.tworld.celebring.celeb.dto.CelebDto;
import com.tworld.celebring.celeb.dto.CelebImageDto;
import com.tworld.celebring.celeb.dto.CelebInfoDto;
import com.tworld.celebring.celeb.model.Celeb;
import com.tworld.celebring.celeb.model.CelebImage;
import com.tworld.celebring.celeb.model.CelebLike;
import com.tworld.celebring.celeb.repository.CelebImageRepository;
import com.tworld.celebring.celeb.repository.CelebLikeRepository;
import com.tworld.celebring.celeb.repository.CelebRepository;
import com.tworld.celebring.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CelebService {
    @Autowired
    private final CelebRepository celebRepository;

    @Autowired
    private final CelebLikeRepository celebLikeRepository;

    @Autowired
    private final CelebImageRepository celebImageRepository;

    public List<CelebDto> getCelebList() {
        List<Celeb> celebList = celebRepository.findAllByDeleteEntityDeleteYn("N");
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> CelebDto.builder().celeb(celeb).build()).collect(Collectors.toList());
        return celebDtoList;
    }

    public List<CelebDto> getGroupCelebListGroupingByConsonant(String startConsonant, String endConsonant, User user) {
        List<Celeb> celebList = celebRepository.findGroupCelebByConsonant("N", startConsonant, endConsonant);
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> {
            Optional<CelebLike> celebLike = user != null ? celebLikeRepository.findOneByIdCelebIdAndIdUserId(celeb.getId(), user.getId()) : null;
            return CelebDto.builder().celeb(celeb).like(user == null || celebLike.isEmpty() ? 0 : 1).build();
        }).collect(Collectors.toList());
        return celebDtoList;
    }
    public List<CelebDto> getSoloCelebListGroupingByConsonant(String startConsonant, String endConsonant, User user) {
        List<Celeb> celebList = celebRepository.findSoloCelebByConsonant("N", startConsonant, endConsonant);
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> {
            Optional<CelebLike> celebLike = user != null ? celebLikeRepository.findOneByIdCelebIdAndIdUserId(celeb.getId(), user.getId()) : null;
            return CelebDto.builder().celeb(celeb).like(user == null || celebLike.isEmpty() ? 0 : 1).build();
        }).collect(Collectors.toList());
        return celebDtoList;
    }

    public List<CelebDto> getFavoriteCelebByUserId(Long userId) {
        List<Celeb> celebList = celebRepository.findAllByDeleteEntityDeleteYnAndLikesIdUserIdOrderByLikesCreateAtAsc("N", userId);
        List<CelebDto> celebDtoList = celebList.stream().map(celeb -> CelebDto.builder().celeb(celeb).build()).collect(Collectors.toList());
        return celebDtoList;
    }

    public List<CelebDto> getSubCelebList(Long celebId, User user) {
        List<Celeb> subCelebList = celebRepository.findAllByDeleteEntityDeleteYnAndSubCelebGroupIdOrderByEventDate("N", celebId);
        List<CelebDto> celebDtoList = subCelebList.stream().map(celeb -> {
            Optional<CelebLike> celebLike = user != null ? celebLikeRepository.findOneByIdCelebIdAndIdUserId(celeb.getId(), user.getId()) : null;
            return CelebDto.builder().celeb(celeb).like(user == null || celebLike.isEmpty() ? 0 : 1).build();
        }).collect(Collectors.toList());
        return celebDtoList;
    }

    public CelebInfoDto getCelebInfo(Long celebId) {
        Optional<Celeb> celeb = celebRepository.findOneById(celebId);
        if(celeb.isPresent()) {
            return CelebInfoDto
                    .builder()
                    .name(celeb.get().getName())
                    .eventDate(celeb.get().getEventDate())
                    .imageList(getCelebImageList(celebId))
                    .build();
        } else {
            return null;
        }
    }

    public List<CelebImageDto> getCelebImageList(Long celebId) {
        List<CelebImage> imageList = celebImageRepository.findAllByCelebIdOrderBySeqDesc(celebId);
        List<CelebImageDto> imageDtoList = imageList.stream().map(image -> CelebImageDto.builder().imageUrl(image.getImageUrl()).seq(image.getSeq()).build()).collect(Collectors.toList());
        return imageDtoList;
    }

    public CelebLike saveCelebLike(Long userId, Long celebId) {
        return celebLikeRepository.save(CelebLike.builder().userId(userId).celebId(celebId).build());
    }
    public void deleteCelebLike(Long userId, Long celebId) {
        celebLikeRepository.delete(CelebLike.builder().userId(userId).celebId(celebId).build());
    }
}
