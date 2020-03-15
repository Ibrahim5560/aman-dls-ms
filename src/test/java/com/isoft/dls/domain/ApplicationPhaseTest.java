package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationPhaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationPhase.class);
        ApplicationPhase applicationPhase1 = new ApplicationPhase();
        applicationPhase1.setId(1L);
        ApplicationPhase applicationPhase2 = new ApplicationPhase();
        applicationPhase2.setId(applicationPhase1.getId());
        assertThat(applicationPhase1).isEqualTo(applicationPhase2);
        applicationPhase2.setId(2L);
        assertThat(applicationPhase1).isNotEqualTo(applicationPhase2);
        applicationPhase1.setId(null);
        assertThat(applicationPhase1).isNotEqualTo(applicationPhase2);
    }
}
