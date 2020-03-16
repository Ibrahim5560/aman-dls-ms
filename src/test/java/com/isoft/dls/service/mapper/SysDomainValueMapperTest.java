package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SysDomainValueMapperTest {

    private SysDomainValueMapper sysDomainValueMapper;

    @BeforeEach
    public void setUp() {
        sysDomainValueMapper = new SysDomainValueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(sysDomainValueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysDomainValueMapper.fromId(null)).isNull();
    }
}
