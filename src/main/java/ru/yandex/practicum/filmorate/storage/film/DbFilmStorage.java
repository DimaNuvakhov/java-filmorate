package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.*;

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
                film.getMpa().getId()
        );
        Film newFilm = getFilm(film.getId());
        return newFilm;
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
                film.getMpa().getId(),
                film.getId()
        );
        Film newFilm = getFilm(film.getId());
        return newFilm;
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
            film.setMpa(findRatingById(film.getRatingId()));
            film.setFilmLikes(makeLikesList(film.getId()));
            film.setGenres(makeGenresList(film.getId()));
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
        film.setMpa(findRatingById(film.getRatingId()));
        film.setFilmLikes(makeLikesList(film.getId()));
        film.setGenres(makeGenresList(film.getId()));
        return film;
    }

    private List<Likes> makeLikesList(Integer filmId) {
        String sql = "select * from likes where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs), filmId);
    }

    private Likes makeLike(ResultSet rs) throws SQLException {
        Likes like = new Likes();
        like.setId(rs.getInt("id"));
        like.setFilmId(rs.getInt("film_id"));
        like.setUserId(rs.getInt("user_id"));
        return like;
    }

    private List<Genres> makeGenresList(Integer filmId) {
        String sql = "select * from genres where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenres(rs), filmId);
    }

    private Genres makeGenres(ResultSet rs) throws SQLException {
        Genres genres = new Genres();
        genres.setId(rs.getInt("id"));
        genres.setFilmId(rs.getInt("film_id"));
        genres.setGenreId(rs.getInt("user_id"));
        genres.setGenre(makeGenreList(genres.getGenreId()));
        return genres;
    }

    private List<Genre> makeGenreList(Integer genreId) {
        String sql = "select * from genre where id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), genreId);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setValue(rs.getString("val"));
        genre.setComment(rs.getString("comm"));
        return genre;
    }
}
