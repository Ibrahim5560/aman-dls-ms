package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class MimeTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MimeTypeDTO.class);
        MimeTypeDTO mimeTypeDTO1 = new MimeTypeDTO();
        mimeTypeDTO1.setId(1L);
        MimeTypeDTO mimeTypeDTO2 = new MimeTypeDTO();
        assertThat(mimeTypeDTO1).isNotEqualTo(mimeTypeDTO2);
        mimeTypeDTO2.setId(mimeTypeDTO1.getId());
        assertThat(mimeTypeDTO1).isEqualTo(mimeTypeDTO2);
        mimeTypeDTO2.setId(2L);
        assertThat(mimeTypeDTO1).isNotEqualTo(mimeTypeDTO2);
        mimeTypeDTO1.setId(null);
        assertThat(mimeTypeDTO1).isNotEqualTo(mimeTypeDTO2);
    }
}
