package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Likes;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.likes.LikesStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesStorage likesStorage;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesStorage = likesStorage;
    }

    public Film createFilm(Film film) {
        if (film.getId() != null) {
            log.error("Фильм еще не добавлен в базу данных, вы не можете передавать id");
            throw new FilmNotFoundException("Фильм еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (film.getName().isBlank()) {
            log.error("Название фильма не может быть пустым");
            throw new InvalidNameException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new InvalidDescriptionException("Максимальная длина описания — 200 символов");
        }
        if (film.getDescription().isBlank()) {
            log.error("Описание фильма не может быть пустым");
            throw new InvalidDescriptionException("Описание фильма не может быть пустым");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
            throw new InvalidDateException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.error("Продолжительность фильма не может быть отрицательной");
            throw new InvalidDurationException("Продолжительность фильма не может быть отрицательной");
        }
        filmStorage.createFilm(film);
        log.info("Фильм " + film.getName() + " добавлен в систему");
        log.debug(film.toString());
        return film;
    }

    public void updateFilm(Film film) {
        if (film.getId() == null || !filmStorage.getAllFilms().containsKey(film.getId())) {
            log.error("Для обновления фильма необходимо передать его корректный id");
            throw new FilmNotFoundException("Для обновления фильма необходимо передать его корректный id");
        }
        if (film.getName().isBlank()) {
            log.error("Название фильма не может быть пустым");
            throw new InvalidNameException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new InvalidDescriptionException("Максимальная длина описания — 200 символов");
        }
        if (film.getDescription().isBlank()) {
            log.error("Описание фильма не может быть пустым");
            throw new InvalidDescriptionException("Описание фильма не может быть пустым");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
            throw new InvalidDateException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.error("Продолжительность фильма не может быть отрицательной");
            throw new InvalidDurationException("Продолжительность фильма не может быть отрицательной");
        }
        filmStorage.updateFilm(film);
        log.info("Фильм под id = " + film.getId() + " обновлен в системе");
        log.debug(film.toString());
    }

    public Collection<Film> getAllFilms() {
        log.debug("Текущее количество добавленных фильмов: {}", filmStorage.getAllFilms().size());
        return filmStorage.getAllFilms().values();
    }

    public Film getFilmById(Integer id) {
        if (!filmStorage.getAllFilms().containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не добавлен в систему", id));
        }
        return filmStorage.getFilm(id);
    }

    public Boolean likeMovie(Integer id, Integer userId) {
        if (!filmStorage.getAllFilms().containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не добавлен в систему", id));
        }
        if (!userStorage.getAllUsers().containsKey(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему", userId));
        }
        Likes like = new Likes();
        like.setFilmId(id);
        like.setUserId(userId);
        likesStorage.createLike(like);
        return true;
    }

    public Boolean removeLike(Integer id, Integer userId) {
        if (!filmStorage.getAllFilms().containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не добавлен в систему", id));
        }
        likesStorage.deleteLike(id, userId);
        return true;
    }

    public List<Film> getTopRatedMovies(Integer count) {
        return filmStorage.getAllFilms().values().stream()
                .sorted((f1,f2) -> f2.getFilmLikes().size() - f1.getFilmLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
