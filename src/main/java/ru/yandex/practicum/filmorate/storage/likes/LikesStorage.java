package ru.yandex.practicum.filmorate.storage.likes;

import ru.yandex.practicum.filmorate.model.Likes;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Map;

public interface LikesStorage {
    Likes createLike(Likes like);

    Likes updateLike(Likes likes);

    Boolean deleteLike(Integer likeId);

    Likes getLike(Integer likeId);

    Map<Integer, Likes> getAllLikes();
}
