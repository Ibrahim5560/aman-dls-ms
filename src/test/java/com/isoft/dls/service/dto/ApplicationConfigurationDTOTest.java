package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationConfigurationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationConfigurationDTO.class);
        ApplicationConfigurationDTO applicationConfigurationDTO1 = new ApplicationConfigurationDTO();
        applicationConfigurationDTO1.setId(1L);
        ApplicationConfigurationDTO applicationConfigurationDTO2 = new ApplicationConfigurationDTO();
        assertThat(applicationConfigurationDTO1).isNotEqualTo(applicationConfigurationDTO2);
        applicationConfigurationDTO2.setId(applicationConfigurationDTO1.getId());
        assertThat(applicationConfigurationDTO1).isEqualTo(applicationConfigurationDTO2);
        applicationConfigurationDTO2.setId(2L);
        assertThat(applicationConfigurationDTO1).isNotEqualTo(applicationConfigurationDTO2);
        applicationConfigurationDTO1.setId(null);
        assertThat(applicationConfigurationDTO1).isNotEqualTo(applicationConfigurationDTO2);
    }
}
