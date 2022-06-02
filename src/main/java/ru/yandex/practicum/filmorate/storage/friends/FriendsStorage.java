package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface FriendsStorage {

    Friends createFriend(Friends friend);

    Friends updateFriend(Friends friend);

    Boolean deleteFriend(Integer friendId);

    Friends getFriend(Integer friendId);

    Map<Integer, Friends> getAllFriends();

    List<User> getCommonFriends(Integer id, Integer otherId);

    Boolean removeFromFriends(Integer id, Integer friendId);

}
