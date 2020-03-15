package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationPhaseMapperTest {

    private ApplicationPhaseMapper applicationPhaseMapper;

    @BeforeEach
    public void setUp() {
        applicationPhaseMapper = new ApplicationPhaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicationPhaseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationPhaseMapper.fromId(null)).isNull();
    }
}
