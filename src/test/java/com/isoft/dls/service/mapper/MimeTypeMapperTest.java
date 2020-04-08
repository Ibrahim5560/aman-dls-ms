package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MimeTypeMapperTest {

    private MimeTypeMapper mimeTypeMapper;

    @BeforeEach
    public void setUp() {
        mimeTypeMapper = new MimeTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(mimeTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mimeTypeMapper.fromId(null)).isNull();
    }
}
