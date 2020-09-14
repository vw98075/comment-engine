package com.vw.commentengine.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vw.commentengine.web.rest.TestUtil;

public class CommentTargetTest {

    @Test
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
