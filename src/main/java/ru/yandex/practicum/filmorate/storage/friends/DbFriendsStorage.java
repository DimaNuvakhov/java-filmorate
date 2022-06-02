package ru.yandex.practicum.filmorate.storage.friends;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class DbFriendsStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    private final UserStorage userStorage;

    private Integer idMax = 0;

    public DbFriendsStorage(JdbcTemplate jdbcTemplate, UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
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

    @Override
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        String sql = "select friend_id from friends where user_id = ? and friend_id " +
                "in (select friend_id from friends where user_id = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> findUserIdAndMakeUser(rs), id, otherId);
    }

    private User findUserIdAndMakeUser(ResultSet rs) throws SQLException {
        Integer userId = rs.getInt("friend_id");
        return userStorage.getUser(userId);
    }

    @Override
    public Boolean removeFromFriends(Integer id, Integer friendId) {
        Integer removedFriendId = 0;
        SqlRowSet rows = jdbcTemplate.queryForRowSet("select id from friends where user_id = ? " +
                "and friend_id = ?", id, friendId);
        if (rows.next()) {
            removedFriendId = rows.getInt("id");
        }
        userStorage.deleteUser(removedFriendId);
        return true;
    }

}
