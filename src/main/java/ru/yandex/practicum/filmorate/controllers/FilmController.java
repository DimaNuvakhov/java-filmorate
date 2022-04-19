package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer idMax = 0;

    public Integer getIdMax() {
        idMax++;
        return idMax;
    }

    // Получение всех фильмов
    @GetMapping
    public Collection<Film> getAllFilms() {
        log.debug("Текущее количество добавленных фильмов: {}", films.size());
        return films.values();
    }

    // Добавление фильма
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if (film.getId() != null) {
            throw new ValidationException("Фильм еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getFilmDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        film.setId(getIdMax());
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " добавлен в систему");
        return film;
    }

    // Обновление фильма
    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        if (film.getId() == null || !films.containsKey(film.getId())) {
            throw new ValidationException("Для обновления фильма необходимо передать его корректный id");
        }
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getFilmDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " обновлен в системе");
        return film;
    }
}
