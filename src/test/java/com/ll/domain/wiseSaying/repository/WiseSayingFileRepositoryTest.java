package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingFileRepositoryTest {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingFileRepository();

    @BeforeEach
    public void beforeEach() {
        Util.file.rmdir("db");
        Util.file.mkdir("db");
    }

    @AfterEach
    public void afterEach() {
        Util.file.rmdir("db");
    }

    @Test
    @DisplayName("명언 저장")
    public void t1() {
        WiseSaying wiseSaying = new WiseSaying(0, "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingRepository.save(wiseSaying);

        String filePath = "db/test/wiseSaying/1.json";

        assertThat(
                Util.file.exists(filePath)
        ).isTrue();

        String jsonStr = Util.file.get(filePath, "");
        Map<String, Object> wiseSayingMap = Util.json.toMap(jsonStr);
        WiseSaying wiseSayingRestored = new WiseSaying(wiseSayingMap);

        assertThat(wiseSayingRestored).isEqualTo(wiseSaying);

    }

    @Test
    @DisplayName("명언 삭제")
    public void t2() {
        WiseSaying wiseSaying = new WiseSaying(0, "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingRepository.save(wiseSaying);

        wiseSayingRepository.deleteById(wiseSaying.getId());

        String filePath = "db/test/wiseSaying/1.json";

        assertThat(
                Util.file.exists(filePath)
        ).isFalse();
    }
}
