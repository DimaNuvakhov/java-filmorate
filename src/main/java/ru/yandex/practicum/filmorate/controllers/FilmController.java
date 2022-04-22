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
public class FilmController extends Controller<Film> {

    // Получение всех фильмов
    @GetMapping
    @Override
    public Collection<Film> getAll() {
        log.debug("Текущее количество добавленных фильмов: {}", items.size());
        return items.values();
    }

    // Добавление фильма
    @PostMapping
    @Override
    public Film add(@RequestBody Film film) {
        if (film.getId() != null) {
            log.error("Фильм еще не добавлен в базу данных, вы не можете передавать id");
            throw new ValidationException("Фильм еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (film.getName().isBlank()) {
            log.error("Название фильма не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.error("Продолжительность фильма не может быть отрицательной");
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        film.setId(getIdMax());
        items.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " добавлен в систему");
        log.debug(film.toString());
        return film;
    }

    // Обновление фильма
    @PutMapping
    @Override
    public Film update(@RequestBody Film film) {
        if (film.getId() == null || !items.containsKey(film.getId())) {
            log.error("Для обновления фильма необходимо передать его корректный id");
            throw new ValidationException("Для обновления фильма необходимо передать его корректный id");
        }
        if (film.getName().isBlank()) {
            log.error("Название фильма не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.error("Продолжительность фильма не может быть отрицательной");
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        items.put(film.getId(), film);
        log.info("Фильм под id = " + film.getId() + " обновлен в системе");
        log.debug(film.toString());
        return film;
    }
}
