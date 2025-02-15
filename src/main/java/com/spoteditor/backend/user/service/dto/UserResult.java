package com.spoteditor.backend.user.service.dto;

import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.user.entity.User;

import java.util.List;

public record UserResult(
        User user,
        Long follower,
        Long following,
        List<PlaceLog> placeLogs,
        List<PlaceLog> bookmarkPlaceLogs,
        List<Place> bookmarkPlaces
) {
    public static UserResult from(
            User user,
            Long follower,
            Long following,
            List<PlaceLog> placeLogs,
            List<PlaceLog> bookmarkPlaceLogs,
            List<Place> bookmarkPlaces
    ){
        return new UserResult(
                user,
                follower,
                following,
                placeLogs,
                bookmarkPlaceLogs,
                bookmarkPlaces
        );
    }
}
