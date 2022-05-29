package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public Rating create(@RequestBody Rating rating) {
       return ratingService.createRating(rating);
    }

    @PutMapping
    public Rating update(@RequestBody Rating rating) {
        return  ratingService.updateRating(rating);
    }

    @DeleteMapping("/{id}")
    public Boolean removeRatingById (@PathVariable Integer id) {
        return ratingService.removeRatingById(id);
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable Integer id) {
        return  ratingService.getRatingById(id);
    }
}
