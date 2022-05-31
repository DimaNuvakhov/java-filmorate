package ru.yandex.practicum.filmorate.storage.friends;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class DbFriendsStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    private Integer idMax = 0;

    public DbFriendsStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Override
    public Friends createFriend(Friends friend) {
        friend.setId(getIdMax());
        String sqlQuery = "insert into friends (id, user_id, friend_id, is_accepted) values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                friend.getId(),
                friend.getUserId(),
                friend.getFriendId(),
                friend.getIsAccepted());
        return friend;
    }

    @Override
    public Friends updateFriend(Friends friend) {
        String sqlQuery = "update friends set user_id = ?, friend_id = ?, is_accepted = ? where id = ?";
        jdbcTemplate.update(sqlQuery,
                friend.getUserId(),
                friend.getFriendId(),
                friend.getIsAccepted(),
                friend.getId());
        return friend;
    }

    @Override
    public Boolean deleteFriend(Integer friendId) {
        String sqlQuery = "delete from friends where id = ?";
        return jdbcTemplate.update(sqlQuery, friendId) > 0;
    }

    @Override
    public Friends getFriend(Integer friendId) {
        SqlRowSet friendRows = jdbcTemplate.queryForRowSet(
                "select id, user_id, friend_id, is_accepted from friends where id = ?",
                friendId);
        Friends friend = new Friends();
        if (friendRows.next()) {
            friend.setId(friendRows.getInt("id"));
            friend.setUserId(friendRows.getInt("user_id"));
            friend.setFriendId(friendRows.getInt("friend_id"));
            friend.setIsAccepted(friendRows.getBoolean("is_accepted"));
        }
        return friend;
    }

    @Override
    public Map<Integer, Friends> getAllFriends() {
        return null;
    }
}
