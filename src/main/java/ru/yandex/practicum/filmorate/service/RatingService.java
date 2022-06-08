package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

@Service
public class RatingService {

    private final RatingStorage ratingStorage;

    @Autowired
    public RatingService(RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    public Rating createRating(Rating rating) {
        return ratingStorage.createRating(rating);
    }

    public Rating updateRating(Rating rating) {
        return ratingStorage.updateRating(rating);
    }

    public Rating getRatingById(Integer ratingId) {
        return ratingStorage.getRating(ratingId);
    }

    public Boolean removeRatingById(Integer ratingId) {
        return ratingStorage.deleteRating(ratingId);
    }
}
