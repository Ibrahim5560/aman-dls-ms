package com.isoft.dls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceRequestMapperTest {

    private ServiceRequestMapper serviceRequestMapper;

    @BeforeEach
    public void setUp() {
        serviceRequestMapper = new ServiceRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceRequestMapper.fromId(null)).isNull();
    }
}
