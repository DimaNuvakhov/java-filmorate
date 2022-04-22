package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Integer, User> users = new HashMap<>();
    private Integer idMax = 0;

    public Integer getIdMax() {
        idMax++;
        return idMax;
    }

    // Получение списка всех пользователей
    @GetMapping
    public Collection<User> getAll() {
        log.debug("Текущее количество добавленных пользователей: {}", users.size());
        return users.values();
    }

    // Создание пользователя
    @PostMapping
    public User add(@RequestBody User user) {
        if (user.getId() != null) {
            log.error("Пользователь еще не добавлен в базу данных, вы не можете передавать id");
            throw new ValidationException("Пользователь еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            log.warn("Имя пользователя не передано, вместо имени установлен переданный логин");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения пользователя не может быть в будущем");
            throw new ValidationException("Дата рождения пользователя не может быть в будущем");
        }
        user.setId(getIdMax());
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getLogin() + " добавлен в систему");
        log.debug(user.toString());
        return user;
    }

    // Обновление пользователя
    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            log.error("Для обновления пользователя необходимо передать его корректный id");
            throw new ValidationException("Для обновления пользователя необходимо передать его корректный id");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            log.warn("Имя пользователя не передано, вместо имени установлен переданный логин");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения пользователя не может быть в будущем");
            throw new ValidationException("Дата рождения пользователя не может быть в будущем");
        }
        users.put(user.getId(), user);
        log.info("Пользователь под id = " + user.getId() + " обновлен в системе");
        log.debug(user.toString());
        return user;
    }
}
