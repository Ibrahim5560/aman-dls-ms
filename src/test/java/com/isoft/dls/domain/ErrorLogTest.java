package com.isoft.dls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.dls.web.rest.TestUtil;

public class ErrorLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrorLog.class);
        ErrorLog errorLog1 = new ErrorLog();
        errorLog1.setId(1L);
        ErrorLog errorLog2 = new ErrorLog();
        errorLog2.setId(errorLog1.getId());
        assertThat(errorLog1).isEqualTo(errorLog2);
        errorLog2.setId(2L);
        assertThat(errorLog1).isNotEqualTo(errorLog2);
        errorLog1.setId(null);
        assertThat(errorLog1).isNotEqualTo(errorLog2);
    }
}
