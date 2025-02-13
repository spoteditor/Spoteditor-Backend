package com.spoteditor.backend.placelog.entity;

import lombok.Getter;

@Getter
public enum TagCategory {
    WITH_WHOM("누구와"),
    MOOD("어떤 느낌으로");

    private final String description;

    TagCategory(String description) {
        this.description = description;
    }
}
