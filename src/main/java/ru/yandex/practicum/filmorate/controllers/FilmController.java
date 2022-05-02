package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Получение всех фильмов
    @GetMapping
    @Override
    public Collection<Film> getAll() {
        return filmService.getAllFilms();
    }

    // Добавление фильма
    @PostMapping
    @Override
    public Film create(@RequestBody Film film) {
        return filmService.createFilm(film);
    }

    // Обновление фильма
    @PutMapping
    @Override
    public Film update(@RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeMovie(@PathVariable Integer id, @PathVariable Integer userId) {
        return null;
    }

    @DeleteMapping("{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        return null;
    }

    @GetMapping("/popular")
    public List<Film> getFilms(
            @RequestParam(defaultValue = "10", required = false) Integer count) {
        return null;
    }
}
