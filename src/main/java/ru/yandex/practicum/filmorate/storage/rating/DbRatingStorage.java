package ru.yandex.practicum.filmorate.storage.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Component
public class DbRatingStorage implements RatingStorage {

    private final JdbcTemplate jdbcTemplate;

    private Integer idMax = 0;

    @Autowired
    public DbRatingStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    //(select max(id) + 1 from rating)

    @Override
    public Rating createRating(Rating rating) {
        rating.setId(getIdMax());
        String sqlQuery = "insert into rating (id, val, comm) values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                rating.getId(),
                rating.getValue(),
                rating.getComment());
        return rating;
    }

    @Override
    public Rating updateRating(Rating rating) {
        String sqlQuery = "update rating set val = ?, comm = ? where id = ?";
        jdbcTemplate.update(sqlQuery,
                rating.getValue(),
                rating.getComment(),
                rating.getId());
        return rating;
    }

    @Override
    public Boolean deleteRating(Integer ratingId) {
        String sqlQuery = "delete from rating where id = ?";
        return jdbcTemplate.update(sqlQuery, ratingId) > 0;
    }

    @Override
    public Rating getRating(Integer ratingId) {
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet(
                "select id, val, comm from rating where id = ?",
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
    public Map<Integer, Rating> getAllRatings() {
        return null;
    }
}
