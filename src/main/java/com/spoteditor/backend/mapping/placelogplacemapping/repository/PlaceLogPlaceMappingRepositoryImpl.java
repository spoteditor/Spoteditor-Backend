package com.spoteditor.backend.mapping.placelogplacemapping.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spoteditor.backend.mapping.placelogplacemapping.entity.QPlaceLogPlaceMapping.placeLogPlaceMapping;

@Repository
@RequiredArgsConstructor
public class PlaceLogPlaceMappingRepositoryImpl implements PlaceLogPlaceMappingRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PlaceLogPlaceMapping> findByPlaceLogId(Long placeLogId) {
        return queryFactory
                .selectFrom(placeLogPlaceMapping)
                .where(placeLogPlaceMapping.placeLog.id.eq(placeLogId))
                .fetch();
    }
}
