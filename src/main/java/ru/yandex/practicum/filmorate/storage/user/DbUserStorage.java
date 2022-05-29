package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        }
        return user;
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        return null;
    }
}
