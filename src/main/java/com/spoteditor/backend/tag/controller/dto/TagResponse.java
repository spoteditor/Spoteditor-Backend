package com.spoteditor.backend.tag.controller.dto;

import com.spoteditor.backend.tag.entity.Tag;
import com.spoteditor.backend.tag.entity.TagCategory;

public record TagResponse (
        String name,
        TagCategory category
) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getName(), tag.getTagCategory());
    }
}
