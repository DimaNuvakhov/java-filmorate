package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        String sqlQuery = "select id, email, login, name,  birthday" +
                "from users where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, userId);
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        return null;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setLogin(resultSet.getString("name"));
        user.setBirthday(birthday);
        return user;
    }

}
