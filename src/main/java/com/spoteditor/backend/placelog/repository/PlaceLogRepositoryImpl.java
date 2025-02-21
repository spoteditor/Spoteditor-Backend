package com.spoteditor.backend.placelog.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spoteditor.backend.mapping.placelogplacemapping.entity.QPlaceLogPlaceMapping.placeLogPlaceMapping;
import static com.spoteditor.backend.mapping.placelogtagmapping.entity.QPlaceLogTagMapping.placeLogTagMapping;
import static com.spoteditor.backend.place.entity.QPlace.place;
import static com.spoteditor.backend.placelog.entity.QPlaceLog.placeLog;
import static com.spoteditor.backend.tag.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class PlaceLogRepositoryImpl implements PlaceLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public CustomPageResponse<PlaceLogResponse> findAllPlace(CustomPageRequest request) {
        PageRequest pageRequest = request.of();

        List<PlaceLogResponse> placeLogList = queryFactory
                .select(Projections.constructor(PlaceLogResponse.class,
                        placeLog.id.as("placeLogId"),
                        placeLog.name,
                        placeLog.description,
                        placeLog.imageUrl.as("image"),
                        placeLog.address,
                        placeLog.views,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(tag)
                                        .from(placeLogTagMapping)
                                        .join(placeLogTagMapping.tag, tag)
                                        .where(placeLogTagMapping.placeLog.id.eq(placeLog.id)),
                                "tags"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(place)
                                        .from(placeLogPlaceMapping)
                                        .join(placeLogPlaceMapping.place, place)
                                        .where(placeLogPlaceMapping.placeLog.id.eq(placeLog.id)),
                                "places"
                        )
                ))
                .from(placeLog)
                .where(placeLog.status.eq(PlaceLogStatus.PUBLIC))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(placeLog.createdAt.asc())
                .fetch();

        JPAQuery<Long> queryCount = queryFactory
                .select(placeLog.count())
                .from(placeLog);

        Page<PlaceLogResponse> page = PageableExecutionUtils.getPage(
                placeLogList,
                pageRequest,
                queryCount::fetchOne
        );

        return new CustomPageResponse<>(page);
    }
}
