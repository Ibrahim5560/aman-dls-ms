package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class SysDomainValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDomainValue.class);
        SysDomainValue sysDomainValue1 = new SysDomainValue();
        sysDomainValue1.setId(1L);
        SysDomainValue sysDomainValue2 = new SysDomainValue();
        sysDomainValue2.setId(sysDomainValue1.getId());
        assertThat(sysDomainValue1).isEqualTo(sysDomainValue2);
        sysDomainValue2.setId(2L);
        assertThat(sysDomainValue1).isNotEqualTo(sysDomainValue2);
        sysDomainValue1.setId(null);
        assertThat(sysDomainValue1).isNotEqualTo(sysDomainValue2);
    }
}
