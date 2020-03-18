package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ErrorLogMapperTest {

    private ErrorLogMapper errorLogMapper;

    @BeforeEach
    public void setUp() {
        errorLogMapper = new ErrorLogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(errorLogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(errorLogMapper.fromId(null)).isNull();
    }
}
