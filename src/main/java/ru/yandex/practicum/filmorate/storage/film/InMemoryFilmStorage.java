package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Integer idMax = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(getIdMax());
        if (films.containsKey(film.getId())) {
            throw new FilmAlreadyExistException(String.format("Фильм с id %d уже добавлен в систему", film.getId()));
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не добавлен в систему", film.getId()));
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Boolean deleteFilm(Integer filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не добавлен в систему", filmId));
        }
        films.remove(filmId);
        return true;
    }

    @Override
    public Film getFilm(Integer filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не добавлен в систему", filmId));
        }
        return films.get(filmId);
    }

    @Override
    public Map<Integer, Film> getAllFilms() {
        return films;
    }
}
