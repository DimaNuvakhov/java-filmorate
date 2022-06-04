package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FriendsService;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    FriendsService friendsService;

    @PostMapping
    public Friends create(@RequestBody Friends friend) {
        return friendsService.createFriend(friend);
    }

    @PutMapping
    public Friends update(@RequestBody Friends friend) {
        return friendsService.updateFriend(friend);
    }

    @DeleteMapping("/{id}")
    public Boolean removeFriendById(@PathVariable Integer id) {
        return friendsService.removeFriendById(id);
    }

    @GetMapping("/{id}")
    public Friends getFriendById(@PathVariable Integer id) {
        return friendsService.getFriendById(id);
    }
}
