package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationTypeDTO.class);
        ApplicationTypeDTO applicationTypeDTO1 = new ApplicationTypeDTO();
        applicationTypeDTO1.setId(1L);
        ApplicationTypeDTO applicationTypeDTO2 = new ApplicationTypeDTO();
        assertThat(applicationTypeDTO1).isNotEqualTo(applicationTypeDTO2);
        applicationTypeDTO2.setId(applicationTypeDTO1.getId());
        assertThat(applicationTypeDTO1).isEqualTo(applicationTypeDTO2);
        applicationTypeDTO2.setId(2L);
        assertThat(applicationTypeDTO1).isNotEqualTo(applicationTypeDTO2);
        applicationTypeDTO1.setId(null);
        assertThat(applicationTypeDTO1).isNotEqualTo(applicationTypeDTO2);
    }
}
