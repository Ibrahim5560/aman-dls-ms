package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class SysDomainTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDomain.class);
        SysDomain sysDomain1 = new SysDomain();
        sysDomain1.setId(1L);
        SysDomain sysDomain2 = new SysDomain();
        sysDomain2.setId(sysDomain1.getId());
        assertThat(sysDomain1).isEqualTo(sysDomain2);
        sysDomain2.setId(2L);
        assertThat(sysDomain1).isNotEqualTo(sysDomain2);
        sysDomain1.setId(null);
        assertThat(sysDomain1).isNotEqualTo(sysDomain2);
    }
}
