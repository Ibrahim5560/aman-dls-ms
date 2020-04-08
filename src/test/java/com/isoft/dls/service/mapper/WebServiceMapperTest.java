package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WebServiceMapperTest {

    private WebServiceMapper webServiceMapper;

    @BeforeEach
    public void setUp() {
        webServiceMapper = new WebServiceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(webServiceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(webServiceMapper.fromId(null)).isNull();
    }
}
