package com.ll.domain.wiseSaying.entity;

import com.ll.wiseSaying.controller.WiseSayingController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public boolean isNew() {
        return id == 0;
    }
}
