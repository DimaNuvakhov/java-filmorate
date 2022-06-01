package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.GenresService;

@RestController
@RequestMapping("/genres")
public class GenresController {

    GenresService genresService;

    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @PostMapping
    public Genres create(@RequestBody Genres genres) {
        return genresService.createGenres(genres);
    }

    @PutMapping
    public Genres update(@RequestBody Genres genres) {
        return genresService.updateGenres(genres);
    }

    @DeleteMapping("/{id}")
    public Boolean removeGenreById(@PathVariable Integer id) {
        return genresService.removeGenresById(id);
    }

    @GetMapping("/{id}")
    public Genres getGenreById (@PathVariable Integer id) {
        return genresService.getGenresById(id);
    }

}
