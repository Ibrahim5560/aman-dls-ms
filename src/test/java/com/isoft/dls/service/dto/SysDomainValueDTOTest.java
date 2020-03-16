package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class SysDomainValueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDomainValueDTO.class);
        SysDomainValueDTO sysDomainValueDTO1 = new SysDomainValueDTO();
        sysDomainValueDTO1.setId(1L);
        SysDomainValueDTO sysDomainValueDTO2 = new SysDomainValueDTO();
        assertThat(sysDomainValueDTO1).isNotEqualTo(sysDomainValueDTO2);
        sysDomainValueDTO2.setId(sysDomainValueDTO1.getId());
        assertThat(sysDomainValueDTO1).isEqualTo(sysDomainValueDTO2);
        sysDomainValueDTO2.setId(2L);
        assertThat(sysDomainValueDTO1).isNotEqualTo(sysDomainValueDTO2);
        sysDomainValueDTO1.setId(null);
        assertThat(sysDomainValueDTO1).isNotEqualTo(sysDomainValueDTO2);
    }
}
