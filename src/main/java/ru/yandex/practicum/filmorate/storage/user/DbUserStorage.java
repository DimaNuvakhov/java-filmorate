package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Primary
public class DbUserStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private Integer idMax = 0;

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }
    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        user.setId(getIdMax());
        String sqlQuery = "insert into users (id, email, login, name, birthday) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set " + "email = ?, login = ?, " +
                "name = ?, birthday = ? " + "where id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        String sqlQuery = "delete from users where id = ?";
        return jdbcTemplate.update(sqlQuery, userId) > 0;
    }

    @Override
    public User getUser(Integer userId) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(
                "select id, email, login, name, birthday from users where id = ?",
                userId);
        User user = new User();
        if (userRows.next()) {
            user.setId(userRows.getInt("id"));
            user.setEmail(userRows.getString("email"));
            user.setLogin(userRows.getString("login"));
            user.setName(userRows.getString("name"));
            LocalDate birthday = userRows.getDate("birthday").toLocalDate();
            user.setBirthday(birthday);
            user.setMyFriends(makeMapFriends(user.getId()));
        }
        return user;
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> map = new HashMap<>();
        List<User> users = makeList();
        for (User user : users) {
            map.put(user.getId(), user);
        }
        return map;
    }

    private  Map<Integer, Friends> makeMapFriends(Integer userId) {
        Map<Integer, Friends> friends = new HashMap<>();
        List<Friends> friendsList = makeFriendsList(userId);
        for (Friends friend : friendsList) {
            friends.put(friend.getId(), friend);
        }
        return friends;
    }

    private  List<Friends> makeFriendsList(Integer userId) {
        String sql = "select * from friends where user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeIdFriend(rs), userId);
    }

    private  Friends makeIdFriend(ResultSet rs) throws SQLException {
        Friends friend = new Friends();
        friend.setId(rs.getInt("id"));
        friend.setUserId(rs.getInt("user_id"));
        friend.setFriendId(rs.getInt("friend_id"));
        friend.setIsAccepted(rs.getBoolean("is_accepted"));
        return friend;
    }

    private List<User> makeList() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        user.setBirthday(birthday);
        return user;
    }
}
