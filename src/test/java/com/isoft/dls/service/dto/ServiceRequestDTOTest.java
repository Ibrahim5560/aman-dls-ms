package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ServiceRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequestDTO.class);
        ServiceRequestDTO serviceRequestDTO1 = new ServiceRequestDTO();
        serviceRequestDTO1.setId(1L);
        ServiceRequestDTO serviceRequestDTO2 = new ServiceRequestDTO();
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
        serviceRequestDTO2.setId(serviceRequestDTO1.getId());
        assertThat(serviceRequestDTO1).isEqualTo(serviceRequestDTO2);
        serviceRequestDTO2.setId(2L);
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
        serviceRequestDTO1.setId(null);
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
    }
}
