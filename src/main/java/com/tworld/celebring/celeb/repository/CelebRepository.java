package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.Celeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CelebRepository extends JpaRepository<Celeb, Long> {

    Optional<Celeb> findOneById(Long id);

    List<Celeb> findAllByDeleteEntityDeleteYn(String deleteYn);

    @Query("select distinct c from celeb c inner join c.memberLink where c.deleteEntity.deleteYn = ?1 and lower(c.name) >= ?2 and lower(c.name) < ?3 order by c.name")
    List<Celeb> findGroupCelebByConsonant(String deleteYn, String startConsonant, String endConsonant);

    @Query("select distinct c from celeb c left outer join c.memberLink m where c.deleteEntity.deleteYn = ?1 and lower(c.name) >= ?2 and lower(c.name) < ?3 and m.groupId is null order by c.name")
    List<Celeb> findSoloCelebByConsonant(String deleteYn, String startConsonant, String endConsonant);

    List<Celeb> findAllByDeleteEntityDeleteYnAndLikesIdUserIdOrderByLikesCreateAtAsc(String deleteYn, Long userId);

    List<Celeb> findAllByDeleteEntityDeleteYnAndSubCelebGroupIdOrderByEventDate(String deleteYn, Long celebId);
}
