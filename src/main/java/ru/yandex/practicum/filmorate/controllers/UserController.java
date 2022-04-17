package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getId() != null) {
            throw new ValidationException("Пользователь еще не добавлен в базу данных, вы не можете передавать id");
        }
        if (user.getEmail().isBlank() && !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() && user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения пользователя не может быть в будущем");
        }
        // Сгенерировать id и установаить его
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        if (user.getId() == null) {
            throw new ValidationException("Для обновления пользователя необходимо передать его id");
        }
        if (user.getEmail().isBlank() && !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() && user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения пользователя не может быть в будущем");
        }
        // Изменить поля по id
        users.put(user.getId(), user);
        return user;
    }
}
