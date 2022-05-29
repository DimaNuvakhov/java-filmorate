package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Map;
@Component
public class DbGenreStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private Integer idMax = 0;

    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Override
    public Genre createGenre(Genre genre) {
        genre.setId(idMax);
        String sqlQuery = "insert into rating (id, val, comm) values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                genre.getId(),
                genre.getValue(),
                genre.getComment());
        return genre;
    }

    @Override
    public Genre updateGenre(Genre genre) {
        String sqlQuery = "update rating set val = ?, comm = ? where id = ?";
        jdbcTemplate.update(sqlQuery,
                genre.getValue(),
                genre.getComment(),
                genre.getId());
        return genre;
    }

    @Override
    public Boolean deleteGenre(Integer genreId) {
        String sqlQuery = "delete from rating where id = ?";
        return jdbcTemplate.update(sqlQuery, genreId) > 0;
    }

    @Override
    public Genre getGenre(Integer genreId) {
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet(
                "select id, val, comm from genre where id = ?",
                genreId);
        Genre genre = new Genre();
        if (ratingRows.next()) {
            genre.setId(ratingRows.getInt("id"));
            genre.setValue(ratingRows.getString("val"));
            genre.setComment(ratingRows.getString("comm"));
        }
        return genre;
    }

    @Override
    public Map<Integer, Genre> getAllGenres() {
        return null;
    }
}
