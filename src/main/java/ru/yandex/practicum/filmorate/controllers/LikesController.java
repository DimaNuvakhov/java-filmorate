package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.Likes;
import ru.yandex.practicum.filmorate.service.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {

    LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping
    public Likes create(@RequestBody Likes like) {
        return likesService.createLike(like);
    }

    @PutMapping
    public Likes update(@RequestBody Likes like) {
        return likesService.updateLike(like);
    }

    @GetMapping("/{id}")
    public Likes getLikeById(@PathVariable Integer id) {
        return likesService.getLikeById(id);
    }
}
