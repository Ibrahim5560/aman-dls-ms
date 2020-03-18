package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WebServicePropertyMapperTest {

    private WebServicePropertyMapper webServicePropertyMapper;

    @BeforeEach
    public void setUp() {
        webServicePropertyMapper = new WebServicePropertyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(webServicePropertyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(webServicePropertyMapper.fromId(null)).isNull();
    }
}
