package com.spoteditor.backend.mapping.userplacelogmapping.repository;

public interface UserPlaceLogMappingRepositoryCustom {

    boolean existsByUserIdAndPlaceLogId(Long userId, Long placeLogId);
}
