package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.Likes;
import ru.yandex.practicum.filmorate.storage.likes.LikesStorage;
@Service
public class LikesService {

    private final LikesStorage likesStorage;

    public LikesService(LikesStorage likesStorage) {
        this.likesStorage = likesStorage;
    }

    public Likes createLike(Likes like) {
        return likesStorage.createLike(like);
    }

    public Likes updateLike(Likes like) {
        return likesStorage.updateLike(like);
    }

    public Likes getLikeById(Integer likeId) {
        return likesStorage.getLike(likeId);
    }

    public Boolean removeFriendById(Integer likeId) {
        return likesStorage.deleteLike(likeId);
    }

}
