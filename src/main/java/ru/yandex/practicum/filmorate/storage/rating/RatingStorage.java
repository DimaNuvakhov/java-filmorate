package ru.yandex.practicum.filmorate.storage.rating;

import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface RatingStorage {
    Rating createRating(Rating rating);

    Rating updateRating(Rating rating);

    Boolean deleteRating(Integer ratingId);

    Rating getRating(Integer ratingId);

    Map<Integer, Rating> getAllRatings();
}
