package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
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
        filmStorage.createFilm(film);
        log.info("Фильм " + film.getName() + " добавлен в систему");
        log.debug(film.toString());
        return film;
    }

    public void updateFilm(Film film) {
        if (film.getId() == null || !filmStorage.getAllFilms().containsKey(film.getId())) {
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
        filmStorage.updateFilm(film);
        log.info("Фильм под id = " + film.getId() + " обновлен в системе");
        log.debug(film.toString());
    }

    public Collection<Film> getAllFilms() {
        log.debug("Текущее количество добавленных фильмов: {}", filmStorage.getAllFilms().size());
        return filmStorage.getAllFilms().values();
    }
}
