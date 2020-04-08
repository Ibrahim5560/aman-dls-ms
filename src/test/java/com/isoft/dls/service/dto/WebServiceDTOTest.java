package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class WebServiceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebServiceDTO.class);
        WebServiceDTO webServiceDTO1 = new WebServiceDTO();
        webServiceDTO1.setId(1L);
        WebServiceDTO webServiceDTO2 = new WebServiceDTO();
        assertThat(webServiceDTO1).isNotEqualTo(webServiceDTO2);
        webServiceDTO2.setId(webServiceDTO1.getId());
        assertThat(webServiceDTO1).isEqualTo(webServiceDTO2);
        webServiceDTO2.setId(2L);
        assertThat(webServiceDTO1).isNotEqualTo(webServiceDTO2);
        webServiceDTO1.setId(null);
        assertThat(webServiceDTO1).isNotEqualTo(webServiceDTO2);
    }
}
