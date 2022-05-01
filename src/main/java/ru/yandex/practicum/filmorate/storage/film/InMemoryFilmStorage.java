package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
        // Сделать валидацию, что есть в мапе ключа иначе исключение, записать id  в переменную и сделать валидацию
        film.setId(getIdMax());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        // Сделать валидацию, что есть в мапе ключа иначе исключение
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Boolean deleteFilm(Integer filmId) {
        // Сделать валидацию, что есть в мапе ключа иначе исключение
        films.remove(filmId);
        return true;
    }

    @Override
    public Film getFilm(Integer filmId) {
        // Сделать валидацию, что есть в мапе ключа иначе исключение
        return films.get(filmId);
    }

    @Override
    public Map<Integer, Film> getAllFilms() {
        return films;
    }
}
