package com.ll.standard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest {
    @Test
    @DisplayName("파일을 생성할 수 있다.")
    public void t1() {
        String filePath = "test.txt";

        Util.file.touch(filePath);

        assertThat(
                Util.file.exists(filePath)
        ).isTrue();
    }
}
