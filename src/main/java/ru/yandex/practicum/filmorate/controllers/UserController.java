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
public class UserController extends Controller<User>{

    // Получение списка всех пользователей
    @GetMapping
    @Override
    public Collection<User> getAll() {
        log.debug("Текущее количество добавленных пользователей: {}", items.size());
        return items.values();
    }

    // Создание пользователя
    @PostMapping
    @Override
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
        items.put(user.getId(), user);
        log.info("Пользователь " + user.getLogin() + " добавлен в систему");
        log.debug(user.toString());
        return user;
    }

    // Обновление пользователя
    @PutMapping
    @Override
    public User update(@RequestBody User user) {
        if (user.getId() == null || !items.containsKey(user.getId())) {
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
        items.put(user.getId(), user);
        log.info("Пользователь под id = " + user.getId() + " обновлен в системе");
        log.debug(user.toString());
        return user;
    }
}
