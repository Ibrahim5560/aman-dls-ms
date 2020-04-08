package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class SysDomainDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDomainDTO.class);
        SysDomainDTO sysDomainDTO1 = new SysDomainDTO();
        sysDomainDTO1.setId(1L);
        SysDomainDTO sysDomainDTO2 = new SysDomainDTO();
        assertThat(sysDomainDTO1).isNotEqualTo(sysDomainDTO2);
        sysDomainDTO2.setId(sysDomainDTO1.getId());
        assertThat(sysDomainDTO1).isEqualTo(sysDomainDTO2);
        sysDomainDTO2.setId(2L);
        assertThat(sysDomainDTO1).isNotEqualTo(sysDomainDTO2);
        sysDomainDTO1.setId(null);
        assertThat(sysDomainDTO1).isNotEqualTo(sysDomainDTO2);
    }
}
