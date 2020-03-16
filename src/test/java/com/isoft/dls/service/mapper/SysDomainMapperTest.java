package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SysDomainMapperTest {

    private SysDomainMapper sysDomainMapper;

    @BeforeEach
    public void setUp() {
        sysDomainMapper = new SysDomainMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(sysDomainMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysDomainMapper.fromId(null)).isNull();
    }
}
