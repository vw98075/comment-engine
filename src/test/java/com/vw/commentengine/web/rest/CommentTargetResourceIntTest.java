package com.vw.commentengine.web.rest;

import com.vw.commentengine.CommentEngineApp;

import com.vw.commentengine.domain.CommentTarget;
import com.vw.commentengine.repository.CommentTargetRepository;
import com.vw.commentengine.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommentTargetResource REST controller.
 *
 * @see CommentTargetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommentEngineApp.class)
public class CommentTargetResourceIntTest {

    private static final String DEFAULT_TARGET_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_ENTITY_NAME = "BBBBBBBBBB";

    @Autowired
    private CommentTargetRepository commentTargetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentTargetMockMvc;

    private CommentTarget commentTarget;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommentTargetResource commentTargetResource = new CommentTargetResource(commentTargetRepository);
        this.restCommentTargetMockMvc = MockMvcBuilders.standaloneSetup(commentTargetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

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

    @Before
    public void initTest() {
        commentTarget = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentTarget() throws Exception {
        int databaseSizeBeforeCreate = commentTargetRepository.findAll().size();

        // Create the CommentTarget
        restCommentTargetMockMvc.perform(post("/api/comment-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentTarget)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetEntityName").value(hasItem(DEFAULT_TARGET_ENTITY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCommentTarget() throws Exception {
        // Initialize the database
        commentTargetRepository.saveAndFlush(commentTarget);

        // Get the commentTarget
        restCommentTargetMockMvc.perform(get("/api/comment-targets/{id}", commentTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentTarget.getId().intValue()))
            .andExpect(jsonPath("$.targetEntityName").value(DEFAULT_TARGET_ENTITY_NAME.toString()));
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
        CommentTarget updatedCommentTarget = commentTargetRepository.findOne(commentTarget.getId());
        updatedCommentTarget
            .targetEntityName(UPDATED_TARGET_ENTITY_NAME);

        restCommentTargetMockMvc.perform(put("/api/comment-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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

        // Create the CommentTarget

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentTargetMockMvc.perform(put("/api/comment-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentTarget)))
            .andExpect(status().isCreated());

        // Validate the CommentTarget in the database
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentTarget() throws Exception {
        // Initialize the database
        commentTargetRepository.saveAndFlush(commentTarget);
        int databaseSizeBeforeDelete = commentTargetRepository.findAll().size();

        // Get the commentTarget
        restCommentTargetMockMvc.perform(delete("/api/comment-targets/{id}", commentTarget.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentTarget> commentTargetList = commentTargetRepository.findAll();
        assertThat(commentTargetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentTarget.class);
        CommentTarget commentTarget1 = new CommentTarget();
        commentTarget1.setId(1L);
        CommentTarget commentTarget2 = new CommentTarget();
        commentTarget2.setId(commentTarget1.getId());
        assertThat(commentTarget1).isEqualTo(commentTarget2);
        commentTarget2.setId(2L);
        assertThat(commentTarget1).isNotEqualTo(commentTarget2);
        commentTarget1.setId(null);
        assertThat(commentTarget1).isNotEqualTo(commentTarget2);
    }
}
