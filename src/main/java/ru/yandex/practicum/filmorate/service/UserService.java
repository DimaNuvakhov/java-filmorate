package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void createUser(User user) {
        if (user.getId() != null) {
            log.error("Пользователь еще не добавлен в базу данных, вы не можете передавать id");
            throw new UserNotFoundException("Пользователь еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @");
            throw new InvalidEmailException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и содержать пробелы");
            throw new InvalidLoginException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            log.warn("Имя пользователя не передано, вместо имени установлен переданный логин");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения пользователя не может быть в будущем");
            throw new InvalidDateException("Дата рождения пользователя не может быть в будущем");
        }
        userStorage.createUser(user);
        log.info("Пользователь " + user.getLogin() + " добавлен в систему");
        log.debug(user.toString());
    }

    public void updateUser(User user) {
        if (user.getId() == null || !userStorage.getAllUsers().containsKey(user.getId())) {
            log.error("Для обновления пользователя необходимо передать его корректный id");
            throw new UserNotFoundException("Для обновления пользователя необходимо передать его корректный id");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @");
            throw new InvalidEmailException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и содержать пробелы");
            throw new InvalidLoginException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            log.warn("Имя пользователя не передано, вместо имени установлен переданный логин");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения пользователя не может быть в будущем");
            throw new InvalidDateException("Дата рождения пользователя не может быть в будущем");
        }
        userStorage.updateUser(user);
        log.info("Пользователь под id = " + user.getId() + " обновлен в системе");
        log.debug(user.toString());
    }

    public Collection<User> getAllUsers() {
        log.debug("Текущее количество добавленных пользователей: {}", userStorage.getAllUsers().size());
        return userStorage.getAllUsers().values();
    }

    public User getUserById(Integer id) {
        return userStorage.getUser(id);
    }
}
