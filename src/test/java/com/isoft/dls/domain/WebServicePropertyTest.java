package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class WebServicePropertyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebServiceProperty.class);
        WebServiceProperty webServiceProperty1 = new WebServiceProperty();
        webServiceProperty1.setId(1L);
        WebServiceProperty webServiceProperty2 = new WebServiceProperty();
        webServiceProperty2.setId(webServiceProperty1.getId());
        assertThat(webServiceProperty1).isEqualTo(webServiceProperty2);
        webServiceProperty2.setId(2L);
        assertThat(webServiceProperty1).isNotEqualTo(webServiceProperty2);
        webServiceProperty1.setId(null);
        assertThat(webServiceProperty1).isNotEqualTo(webServiceProperty2);
    }
}
