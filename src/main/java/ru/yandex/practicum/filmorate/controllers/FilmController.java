package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collection;

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
}
