package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.CelebLike;
import com.tworld.celebring.celeb.model.CelebLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebLikeRepository extends JpaRepository<CelebLike, CelebLikeId> {

    CelebLike save(CelebLike celebLike);
    void delete(CelebLike celebLike);
}
