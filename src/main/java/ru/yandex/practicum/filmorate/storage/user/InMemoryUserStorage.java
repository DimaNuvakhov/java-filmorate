package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Integer idMax = 0;
    private final Map<Integer, User> users = new HashMap<>();

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    @Override
    public User createUser(User user) {
        user.setId(getIdMax());
        if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException(String.format("Пользователь с id %d уже добавлен в систему",
                    user.getId()));
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему",
                    user.getId()));
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему",
                    userId));
        }
        users.remove(userId);
        return true;
    }

    @Override
    public User getUser(Integer userId) {
        return users.get(userId);
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        return users;
    }
}
