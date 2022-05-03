package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
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

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Boolean likeMovie(@PathVariable Integer id, @PathVariable Integer userId) {
        return filmService.likeMovie(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public Boolean removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        return filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Integer> getFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        List<Integer> list = filmService.getTopRatedMovies(count);
        return list;
    }
}
