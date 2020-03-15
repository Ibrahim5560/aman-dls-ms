package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationPhaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationPhaseDTO.class);
        ApplicationPhaseDTO applicationPhaseDTO1 = new ApplicationPhaseDTO();
        applicationPhaseDTO1.setId(1L);
        ApplicationPhaseDTO applicationPhaseDTO2 = new ApplicationPhaseDTO();
        assertThat(applicationPhaseDTO1).isNotEqualTo(applicationPhaseDTO2);
        applicationPhaseDTO2.setId(applicationPhaseDTO1.getId());
        assertThat(applicationPhaseDTO1).isEqualTo(applicationPhaseDTO2);
        applicationPhaseDTO2.setId(2L);
        assertThat(applicationPhaseDTO1).isNotEqualTo(applicationPhaseDTO2);
        applicationPhaseDTO1.setId(null);
        assertThat(applicationPhaseDTO1).isNotEqualTo(applicationPhaseDTO2);
    }
}
