package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.Celeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelebRepository extends JpaRepository<Celeb, Long> {
    List<Celeb> findAllByDeleteEntityDeleteYn(String deleteYn);
}
