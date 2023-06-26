package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.CelebImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CelebImageRepository extends JpaRepository<CelebImage, Long> {

    List<CelebImage> findAllByCelebIdOrderBySeqDesc(Long celebId);
}
