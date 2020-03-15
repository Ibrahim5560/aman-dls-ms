package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationViolationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationViolation.class);
        ApplicationViolation applicationViolation1 = new ApplicationViolation();
        applicationViolation1.setId(1L);
        ApplicationViolation applicationViolation2 = new ApplicationViolation();
        applicationViolation2.setId(applicationViolation1.getId());
        assertThat(applicationViolation1).isEqualTo(applicationViolation2);
        applicationViolation2.setId(2L);
        assertThat(applicationViolation1).isNotEqualTo(applicationViolation2);
        applicationViolation1.setId(null);
        assertThat(applicationViolation1).isNotEqualTo(applicationViolation2);
    }
}
