package com.spoteditor.backend.placelog.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TagCategory {
    WITH_WHOM("누구와"),
    MOOD("어떤 느낌으로");

    private final String description;

    TagCategory(String description) {
        this.description = description;
    }

    @JsonCreator
    public static TagCategory from(String value) {
        return Arrays.stream(TagCategory.values())
                .filter(category -> category.name().equalsIgnoreCase(value) || category.description.equals(value))
                .findFirst()
                .orElseThrow(() -> new TagException(INVALID_TAG_CATEGORY))
    }
}
