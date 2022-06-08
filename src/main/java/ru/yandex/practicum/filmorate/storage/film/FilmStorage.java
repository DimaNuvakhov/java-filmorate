package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Boolean deleteFilm(Integer filmId);

    Film getFilm(Integer filmId);

    Map<Integer, Film> getAllFilms();
}
