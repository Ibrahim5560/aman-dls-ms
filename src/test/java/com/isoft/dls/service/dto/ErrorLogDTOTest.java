package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ErrorLogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrorLogDTO.class);
        ErrorLogDTO errorLogDTO1 = new ErrorLogDTO();
        errorLogDTO1.setId(1L);
        ErrorLogDTO errorLogDTO2 = new ErrorLogDTO();
        assertThat(errorLogDTO1).isNotEqualTo(errorLogDTO2);
        errorLogDTO2.setId(errorLogDTO1.getId());
        assertThat(errorLogDTO1).isEqualTo(errorLogDTO2);
        errorLogDTO2.setId(2L);
        assertThat(errorLogDTO1).isNotEqualTo(errorLogDTO2);
        errorLogDTO1.setId(null);
        assertThat(errorLogDTO1).isNotEqualTo(errorLogDTO2);
    }
}
