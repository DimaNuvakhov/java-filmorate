package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
        String sqlQuery = "insert into films (id, name, description, releaseDate, duration, rating_id) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRatingId()
        );
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set name = ?, description = ?, " +
                "releaseDate = ?, duration = ?, rating_id = ? where id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRatingId(),
                film.getId()
        );
        return film;
    }

    @Override
    public Boolean deleteFilm(Integer filmId) {
        String sqlQuery = "delete from films where id = ?";
        return jdbcTemplate.update(sqlQuery, filmId) > 0;
    }

    @Override
    public Film getFilm(Integer filmId) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(
                "select id, name, description, releaseDate, duration, rating_id from films where id = ?",
                filmId);
        Film film = new Film();
        if (filmRows.next()) {
            film.setId(filmRows.getInt("id"));
            film.setName(filmRows.getString("name"));
            film.setDescription(filmRows.getString("description"));
            LocalDate releaseDate = filmRows.getDate("releaseDate").toLocalDate();
            film.setReleaseDate(releaseDate);
            film.setDuration(filmRows.getInt("duration"));
            film.setRatingId(filmRows.getInt("rating_id"));
            film.setFilmRating(findRatingById(film.getRatingId()));
        }
        return film;
    }

    private Rating findRatingById(Integer ratingId) {
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet(
            "select * from rating where id = ?",
            ratingId);
        Rating rating = new Rating();
        if (ratingRows.next()) {
            rating.setId(ratingRows.getInt("id"));
            rating.setValue(ratingRows.getString("val"));
            rating.setComment(ratingRows.getString("comm"));
        }
        return rating;
    }

    @Override
    public Map<Integer, Film> getAllFilms() {
        Map<Integer, Film> films = new HashMap<>();
        List<Film> filmsList = makeList();
        for (Film film : filmsList) {
            films.put(film.getId(), film);
        }
        return films;
    }

    private List<Film> makeList() {
        String sql = "select * from films";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
        film.setReleaseDate(releaseDate);
        film.setDuration(rs.getInt("duration"));
        film.setRatingId(rs.getInt("rating_id"));
        film.setFilmRating(findRatingById(film.getId()));
        return film;
    }
}
