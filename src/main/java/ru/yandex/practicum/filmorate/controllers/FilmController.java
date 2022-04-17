package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if (film.getId() != null) {
            throw new ValidationException("Фильм еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() < 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getFilmDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        // Сгенерировать id и установаить его
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        if (film.getId() == null || !films.containsKey(film.getId())) {
            throw new ValidationException("Для обновления фильма необходимо передать его корректный id");
        }
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() < 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getFilmDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        // Изменить поля по id
        films.put(film.getId(), film);
        return film;
    }
}
