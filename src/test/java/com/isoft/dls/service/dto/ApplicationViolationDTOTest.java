package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationViolationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationViolationDTO.class);
        ApplicationViolationDTO applicationViolationDTO1 = new ApplicationViolationDTO();
        applicationViolationDTO1.setId(1L);
        ApplicationViolationDTO applicationViolationDTO2 = new ApplicationViolationDTO();
        assertThat(applicationViolationDTO1).isNotEqualTo(applicationViolationDTO2);
        applicationViolationDTO2.setId(applicationViolationDTO1.getId());
        assertThat(applicationViolationDTO1).isEqualTo(applicationViolationDTO2);
        applicationViolationDTO2.setId(2L);
        assertThat(applicationViolationDTO1).isNotEqualTo(applicationViolationDTO2);
        applicationViolationDTO1.setId(null);
        assertThat(applicationViolationDTO1).isNotEqualTo(applicationViolationDTO2);
    }
}
