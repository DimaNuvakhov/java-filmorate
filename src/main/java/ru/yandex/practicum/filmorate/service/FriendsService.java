package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;

@Service
public class FriendsService {

    private final FriendsStorage friendsStorage;

    @Autowired
    public FriendsService(FriendsStorage friendsStorage) {
        this.friendsStorage = friendsStorage;
    }


    public Friends createFriend(Friends friend) {
        return friendsStorage.createFriend(friend);
    }

    public Friends updateFriend(Friends friend) {
        return friendsStorage.updateFriend(friend);
    }

    public Friends getFriendById(Integer friendId) {
        return friendsStorage.getFriend(friendId);
    }

    public Boolean removeFriendById(Integer friendId) {
        return friendsStorage.deleteFriend(friendId);
    }

}
