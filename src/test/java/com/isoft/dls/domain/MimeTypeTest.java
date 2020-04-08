package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class MimeTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MimeType.class);
        MimeType mimeType1 = new MimeType();
        mimeType1.setId(1L);
        MimeType mimeType2 = new MimeType();
        mimeType2.setId(mimeType1.getId());
        assertThat(mimeType1).isEqualTo(mimeType2);
        mimeType2.setId(2L);
        assertThat(mimeType1).isNotEqualTo(mimeType2);
        mimeType1.setId(null);
        assertThat(mimeType1).isNotEqualTo(mimeType2);
    }
}
