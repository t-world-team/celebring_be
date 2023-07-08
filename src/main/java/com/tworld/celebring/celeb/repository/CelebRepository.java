package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.Celeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CelebRepository extends JpaRepository<Celeb, Long> {

    <T extends Celeb> T save(T celeb);

    Optional<Celeb> findOneById(Long id);

    List<Celeb> findAllByDeleteEntityDeleteYnAndConfirmYn(String deleteYn, String confirmYn);

    @Query("select distinct c from celeb c inner join c.memberLink where c.deleteEntity.deleteYn = ?1 and c.confirmYn = ?2 and lower(c.name) >= ?3 and lower(c.name) < ?4 order by c.name")
    List<Celeb> findGroupCelebByConsonant(String deleteYn, String confirmYn, String startConsonant, String endConsonant);

    @Query("select distinct c from celeb c left outer join c.memberLink m where c.deleteEntity.deleteYn = ?1 and c.confirmYn = ?2 and lower(c.name) >= ?3 and lower(c.name) < ?4 and m.id.groupId is null order by c.name")
    List<Celeb> findSoloCelebByConsonant(String deleteYn, String confirmYn, String startConsonant, String endConsonant);

    List<Celeb> findAllByDeleteEntityDeleteYnAndConfirmYnAndLikesIdUserIdOrderByLikesCreateAtAsc(String deleteYn, String confirmYn, Long userId);

    List<Celeb> findAllByDeleteEntityDeleteYnAndConfirmYnAndSubCelebIdGroupIdOrderByEventDate(String deleteYn, String confirmYn, Long celebId);
}
