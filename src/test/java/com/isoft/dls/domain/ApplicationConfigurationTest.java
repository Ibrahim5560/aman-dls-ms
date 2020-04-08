package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ApplicationConfigurationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationConfiguration.class);
        ApplicationConfiguration applicationConfiguration1 = new ApplicationConfiguration();
        applicationConfiguration1.setId(1L);
        ApplicationConfiguration applicationConfiguration2 = new ApplicationConfiguration();
        applicationConfiguration2.setId(applicationConfiguration1.getId());
        assertThat(applicationConfiguration1).isEqualTo(applicationConfiguration2);
        applicationConfiguration2.setId(2L);
        assertThat(applicationConfiguration1).isNotEqualTo(applicationConfiguration2);
        applicationConfiguration1.setId(null);
        assertThat(applicationConfiguration1).isNotEqualTo(applicationConfiguration2);
    }
}
