package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ApplicationConfigurationMapperTest {

    private ApplicationConfigurationMapper applicationConfigurationMapper;

    @BeforeEach
    public void setUp() {
        applicationConfigurationMapper = new ApplicationConfigurationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(applicationConfigurationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationConfigurationMapper.fromId(null)).isNull();
    }
}
