package com.vw.commentengine.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vw.commentengine.domain.CommentTarget;

import com.vw.commentengine.repository.CommentTargetRepository;
import com.vw.commentengine.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommentTarget.
 */
@RestController
@RequestMapping("/api")
public class CommentTargetResource {

    private final Logger log = LoggerFactory.getLogger(CommentTargetResource.class);

    private static final String ENTITY_NAME = "commentTarget";

    private final CommentTargetRepository commentTargetRepository;

    public CommentTargetResource(CommentTargetRepository commentTargetRepository) {
        this.commentTargetRepository = commentTargetRepository;
    }

    /**
     * POST  /comment-targets : Create a new commentTarget.
     *
     * @param commentTarget the commentTarget to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentTarget, or with status 400 (Bad Request) if the commentTarget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comment-targets")
    @Timed
    public ResponseEntity<CommentTarget> createCommentTarget(@RequestBody CommentTarget commentTarget) throws URISyntaxException {
        log.debug("REST request to save CommentTarget : {}", commentTarget);
        if (commentTarget.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commentTarget cannot already have an ID")).body(null);
        }
        CommentTarget result = commentTargetRepository.save(commentTarget);
        return ResponseEntity.created(new URI("/api/comment-targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comment-targets : Updates an existing commentTarget.
     *
     * @param commentTarget the commentTarget to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentTarget,
     * or with status 400 (Bad Request) if the commentTarget is not valid,
     * or with status 500 (Internal Server Error) if the commentTarget couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comment-targets")
    @Timed
    public ResponseEntity<CommentTarget> updateCommentTarget(@RequestBody CommentTarget commentTarget) throws URISyntaxException {
        log.debug("REST request to update CommentTarget : {}", commentTarget);
        if (commentTarget.getId() == null) {
            return createCommentTarget(commentTarget);
        }
        CommentTarget result = commentTargetRepository.save(commentTarget);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentTarget.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comment-targets : get all the commentTargets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commentTargets in body
     */
    @GetMapping("/comment-targets")
    @Timed
    public List<CommentTarget> getAllCommentTargets() {
        log.debug("REST request to get all CommentTargets");
        return commentTargetRepository.findAll();
    }

    /**
     * GET  /comment-targets/:id : get the "id" commentTarget.
     *
     * @param id the id of the commentTarget to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentTarget, or with status 404 (Not Found)
     */
    @GetMapping("/comment-targets/{id}")
    @Timed
    public ResponseEntity<CommentTarget> getCommentTarget(@PathVariable Long id) {
        log.debug("REST request to get CommentTarget : {}", id);
        CommentTarget commentTarget = commentTargetRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentTarget));
    }

    /**
     * DELETE  /comment-targets/:id : delete the "id" commentTarget.
     *
     * @param id the id of the commentTarget to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comment-targets/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentTarget(@PathVariable Long id) {
        log.debug("REST request to delete CommentTarget : {}", id);
        commentTargetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
