package com.isoft.dls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class WebServicePropertyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebServicePropertyDTO.class);
        WebServicePropertyDTO webServicePropertyDTO1 = new WebServicePropertyDTO();
        webServicePropertyDTO1.setId(1L);
        WebServicePropertyDTO webServicePropertyDTO2 = new WebServicePropertyDTO();
        assertThat(webServicePropertyDTO1).isNotEqualTo(webServicePropertyDTO2);
        webServicePropertyDTO2.setId(webServicePropertyDTO1.getId());
        assertThat(webServicePropertyDTO1).isEqualTo(webServicePropertyDTO2);
        webServicePropertyDTO2.setId(2L);
        assertThat(webServicePropertyDTO1).isNotEqualTo(webServicePropertyDTO2);
        webServicePropertyDTO1.setId(null);
        assertThat(webServicePropertyDTO1).isNotEqualTo(webServicePropertyDTO2);
    }
}
