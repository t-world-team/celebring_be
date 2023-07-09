package com.tworld.celebring.celeb.repository;

import com.tworld.celebring.celeb.model.CelebLink;
import com.tworld.celebring.celeb.model.CelebLinkId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebLinkRepository extends JpaRepository<CelebLink, CelebLinkId> {

    <T extends  CelebLink> T save(T celebLink);
}
