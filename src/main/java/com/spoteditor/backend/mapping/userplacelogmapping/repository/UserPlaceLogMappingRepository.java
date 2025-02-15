package com.spoteditor.backend.mapping.userplacelogmapping.repository;

import com.spoteditor.backend.mapping.userplacelogmapping.entity.UserPlaceLogMapping;
import com.spoteditor.backend.mapping.userplacelogmapping.entity.UserPlaceLogMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlaceLogMappingRepository extends JpaRepository<UserPlaceLogMapping, UserPlaceLogMappingId>, UserPlaceLogMappingRepositoryCustom {
}
