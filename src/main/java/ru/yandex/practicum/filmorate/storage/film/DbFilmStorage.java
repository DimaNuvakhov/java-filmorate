package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

@Component
@Primary
public class DbFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private Integer idMax = 0;

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(getIdMax());
        String sqlQuery = "insert into films (id, name, description, releaseDate, duration, ratingId) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration()
                // TODO Нужно уточнить!
                );
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Boolean deleteFilm(Integer filmId) {
        return null;
    }

    @Override
    public Film getFilm(Integer filmId) {
        return null;
    }

    @Override
    public Map<Integer, Film> getAllFilms() {
        return null;
    }
}
