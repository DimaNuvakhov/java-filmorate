package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    Boolean deleteUser(Integer userId);

    User getUser(Integer userId);

    Map<Integer, User> getAllUsers();// Все пользователи
}
