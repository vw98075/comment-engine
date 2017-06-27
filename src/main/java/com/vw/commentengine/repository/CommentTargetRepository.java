package com.vw.commentengine.repository;

import com.vw.commentengine.domain.CommentTarget;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommentTarget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentTargetRepository extends JpaRepository<CommentTarget,Long> {
    
}
