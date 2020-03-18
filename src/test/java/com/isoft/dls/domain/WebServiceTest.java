package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class WebServiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebService.class);
        WebService webService1 = new WebService();
        webService1.setId(1L);
        WebService webService2 = new WebService();
        webService2.setId(webService1.getId());
        assertThat(webService1).isEqualTo(webService2);
        webService2.setId(2L);
        assertThat(webService1).isNotEqualTo(webService2);
        webService1.setId(null);
        assertThat(webService1).isNotEqualTo(webService2);
    }
}
