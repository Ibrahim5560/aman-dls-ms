package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTypeMapperTest {

    private ApplicationTypeMapper applicationTypeMapper;

    @BeforeEach
    public void setUp() {
        applicationTypeMapper = new ApplicationTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicationTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationTypeMapper.fromId(null)).isNull();
    }
}
