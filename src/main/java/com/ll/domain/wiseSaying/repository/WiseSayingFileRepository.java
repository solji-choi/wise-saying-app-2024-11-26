package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class WiseSayingFileRepository implements WiseSayingRepository {
    public static String getTableDirPath() {
        return "db/test/wiseSaying";
    }

    public static String getRowFilePath(int id) {
        return getTableDirPath() + "/" + id + ".json";
    }

    public static String getLastIdPath() {
        return getTableDirPath() + "/lastId.txt";
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        if (!wiseSaying.isNew()) {
            return wiseSaying;
        }

        int id = getLastId() + 1;

        wiseSaying.setId(id);

        Map<String, Object> wiseSayingMap = wiseSaying.toMap();
        String jsonStr = Util.json.toString(wiseSayingMap);

        Util.file.set(getRowFilePath(wiseSaying.getId()), jsonStr);

        setLastId(id);

        return wiseSaying;
    }

    public List<WiseSaying> findAll() {
        try {
            return Util.file.walkRegularFiles(
                            getTableDirPath(),
                            "\\d+\\.json"
                    )
                    .map(path -> Util.file.get(path.toString(), ""))
                    .map(Util.json::toMap)
                    .map(WiseSaying::new)
                    .toList();
        } catch (NoSuchFileException e) {
            return List.of();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(int id) {
        return Util.file.delete(getRowFilePath(id));
    }

    public Optional<WiseSaying> findById(int id) {
        String filePath = getRowFilePath(id);

        if (Util.file.notExists(filePath)) {

            return Optional.empty();
        }

        String jsonStr = Util.file.get(filePath, "");

        if(jsonStr.isEmpty()) {
            return Optional.empty();
        }

        Map<String, Object> wiseSayingMap = Util.json.toMap(jsonStr);

        return Optional.of(new WiseSaying(wiseSayingMap));
    }

    public int getLastId() {
        return Util.file.getAsInt(getLastIdPath(), 0);
    }

    private void setLastId(int id) {
        Util.file.set(getLastIdPath(), id);
    }
}
