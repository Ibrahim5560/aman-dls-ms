package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationViolationMapperTest {

    private ApplicationViolationMapper applicationViolationMapper;

    @BeforeEach
    public void setUp() {
        applicationViolationMapper = new ApplicationViolationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicationViolationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationViolationMapper.fromId(null)).isNull();
    }
}
