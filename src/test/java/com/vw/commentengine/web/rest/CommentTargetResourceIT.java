package com.vw.commentengine.web.rest;

import com.vw.commentengine.CommentEngineApp;
import com.vw.commentengine.domain.CommentTarget;
import com.vw.commentengine.repository.CommentTargetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommentTargetResource} REST controller.
 */
@SpringBootTest(classes = CommentEngineApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentTargetResourceIT {

    private static final String DEFAULT_TARGET_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_ENTITY_NAME = "BBBBBBBBBB";

    @Autowired
    private CommentTargetRepository commentTargetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentTargetMockMvc;

    private CommentTarget commentTarget;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentTarget createEntity(EntityManager em) {
        CommentTarget commentTarget = new CommentTarget()
            .targetEntityName(DEFAULT_TARGET_ENTITY_NAME);
        return commentTarget;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentTarget createUpdatedEntity(EntityManager em) {
        CommentTarget commentTarget = new CommentTarget()
            .targetEntityName(UPDATED_TARGET_ENTITY_NAME);
        return commentTarget;
    }

    @BeforeEach
    public void initTest() {
        commentTarget = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentTarget() throws Exception {
        int databaseSizeBeforeCreate = commentTargetRepository.findAll().size();
        // Create the CommentTarget
        restCommentTargetMockMvc.perform(post("/api/comment-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentTarget)))
            .andExpect(status().isCreated());

        // Validate the CommentTarget in the database
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeCreate + 1);
        CommentTarget testCommentTarget = commentTargetList.get(commentTargetList.size() - 1);
        assertThat(testCommentTarget.getTargetEntityName()).isEqualTo(DEFAULT_TARGET_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void createCommentTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentTargetRepository.findAll().size();

        // Create the CommentTarget with an existing ID
        commentTarget.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentTargetMockMvc.perform(post("/api/comment-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentTarget)))
            .andExpect(status().isBadRequest());

        // Validate the CommentTarget in the database
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommentTargets() throws Exception {
        // Initialize the database
        commentTargetRepository.saveAndFlush(commentTarget);

        // Get all the commentTargetList
        restCommentTargetMockMvc.perform(get("/api/comment-targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetEntityName").value(hasItem(DEFAULT_TARGET_ENTITY_NAME)));
    }
    
    @Test
    @Transactional
    public void getCommentTarget() throws Exception {
        // Initialize the database
        commentTargetRepository.saveAndFlush(commentTarget);

        // Get the commentTarget
        restCommentTargetMockMvc.perform(get("/api/comment-targets/{id}", commentTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commentTarget.getId().intValue()))
            .andExpect(jsonPath("$.targetEntityName").value(DEFAULT_TARGET_ENTITY_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingCommentTarget() throws Exception {
        // Get the commentTarget
        restCommentTargetMockMvc.perform(get("/api/comment-targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentTarget() throws Exception {
        // Initialize the database
        commentTargetRepository.saveAndFlush(commentTarget);

        int databaseSizeBeforeUpdate = commentTargetRepository.findAll().size();

        // Update the commentTarget
        CommentTarget updatedCommentTarget = commentTargetRepository.findById(commentTarget.getId()).get();
        // Disconnect from session so that the updates on updatedCommentTarget are not directly saved in db
        em.detach(updatedCommentTarget);
        updatedCommentTarget
            .targetEntityName(UPDATED_TARGET_ENTITY_NAME);

        restCommentTargetMockMvc.perform(put("/api/comment-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentTarget)))
            .andExpect(status().isOk());

        // Validate the CommentTarget in the database
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeUpdate);
        CommentTarget testCommentTarget = commentTargetList.get(commentTargetList.size() - 1);
        assertThat(testCommentTarget.getTargetEntityName()).isEqualTo(UPDATED_TARGET_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentTarget() throws Exception {
        int databaseSizeBeforeUpdate = commentTargetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentTargetMockMvc.perform(put("/api/comment-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentTarget)))
            .andExpect(status().isBadRequest());

        // Validate the CommentTarget in the database
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommentTarget() throws Exception {
        // Initialize the database
        commentTargetRepository.saveAndFlush(commentTarget);

        int databaseSizeBeforeDelete = commentTargetRepository.findAll().size();

        // Delete the commentTarget
        restCommentTargetMockMvc.perform(delete("/api/comment-targets/{id}", commentTarget.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
