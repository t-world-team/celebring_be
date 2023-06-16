package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.CelebLike;
import com.tworld.celebring.celeb.model.CelebLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CelebLikeRepository extends JpaRepository<CelebLike, CelebLikeId> {

    <T extends CelebLike> T save(T celebLike);
    void delete(CelebLike celebLike);

    Optional<CelebLike> findOneByIdCelebIdAndIdUserId(Long celebId, Long userId);
}
