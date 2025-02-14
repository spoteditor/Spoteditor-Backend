package com.spoteditor.backend.tag.repository;

import com.spoteditor.backend.tag.entity.Tag;

import java.util.List;

public interface TagRepositoryCustom {

    List<Tag> findTagsByPlaceLogId(Long placeLogId);

    List<Tag> findByNameIn(List<String> tags);
}
