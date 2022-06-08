package ru.yandex.practicum.filmorate.storage.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.Map;

@Component
public class DbGenresStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    private Integer idMax = 0;

    @Autowired
    public DbGenresStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Override
    public Genres createGenres(Genres genres) {
        genres.setId(getIdMax());
        String sqlQuery = "insert into genres (id, film_id, genre_id) values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                genres.getId(),
                genres.getFilmId(),
                genres.getGenreId());
        return genres;
    }

    @Override
    public Genres updateGenres(Genres genres) {
        String sqlQuery = "update genres set film_id = ?, genre_id = ? where id = ?";
        jdbcTemplate.update(sqlQuery,
                genres.getFilmId(),
                genres.getGenreId(),
                genres.getId());
        return genres;
    }

    @Override
    public Boolean deleteGenres(Integer genresId) {
        String sqlQuery = "delete from genres where id = ?";
        return jdbcTemplate.update(sqlQuery, genresId) > 0;
    }

    @Override
    public Genres getGenres(Integer genresId) {
        SqlRowSet genresRows = jdbcTemplate.queryForRowSet(
                "select id, film_id, genre_id from genres where id = ?",
                genresId);
        Genres genres = new Genres();
        if (genresRows.next()) {
            genres.setId(genresRows.getInt("id"));
            genres.setFilmId(genresRows.getInt("film_id"));
            genres.setGenreId(genresRows.getInt("genre_id"));
        }
        return genres;
    }
}
