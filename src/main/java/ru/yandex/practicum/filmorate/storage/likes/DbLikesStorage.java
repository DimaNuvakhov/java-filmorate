package ru.yandex.practicum.filmorate.storage.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Likes;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

@Component
public class DbLikesStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;
    private Integer idMax = 0;

    @Autowired
    public DbLikesStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Override
    public Likes createLike(Likes like) {
        like.setId(getIdMax());
        String sqlQuery = "insert into likes (id, film_id, user_id) " +
                "values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                like.getId(),
                like.getFilmId(),
                like.getUserId());
        return like;
    }

    @Override
    public Likes updateLike(Likes like) {
        String sqlQuery = "update likes set film_id = ?, " +
                "user_id = ? where id = ?";
        jdbcTemplate.update(sqlQuery,
                like.getFilmId(),
                like.getUserId(),
                like.getId());
        return like;
    }

    @Override
    public Boolean deleteLike(Integer id, Integer userId) {
        Integer removedLikeId = 0;
        SqlRowSet rows = jdbcTemplate.queryForRowSet("select id from likes where film_id = ? " +
                "and user_id = ?", id, userId);
        if (rows.next()) {
            removedLikeId = rows.getInt("id");
        }
        return removeLikeById(removedLikeId);
    }

    private Boolean removeLikeById(Integer likeId) {
        String sqlQuery = "delete from likes where id = ?";
        return jdbcTemplate.update(sqlQuery, likeId) > 0;
    }

    @Override
    public Likes getLike(Integer likeId) {
        SqlRowSet likeRows = jdbcTemplate.queryForRowSet(
                "select id, film_id, user_id from likes where id = ?",
                likeId);
        Likes like = new Likes();
        if (likeRows.next()) {
            like.setId(likeRows.getInt("id"));
            like.setFilmId(likeRows.getInt("film_id"));
            like.setUserId(likeRows.getInt("user_id"));
        }
        return like;
    }


}
