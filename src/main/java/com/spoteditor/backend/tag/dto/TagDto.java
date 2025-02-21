package com.spoteditor.backend.tag.dto;

import com.spoteditor.backend.tag.entity.Tag;
import com.spoteditor.backend.tag.entity.TagCategory;

public record TagDto (
        String name,
        TagCategory category
) {
    public static TagDto from(Tag tag) {
        return new TagDto(
                tag.getName(),
                tag.getTagCategory()
        );
    }
}
