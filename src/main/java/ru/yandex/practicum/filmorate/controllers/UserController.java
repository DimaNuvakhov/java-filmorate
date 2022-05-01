package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<User>{
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получение списка всех пользователей
    @GetMapping
    @Override
    public Collection<User> getAll() {
        return userService.getAllUsers();
    }

    // Создание пользователя
    @PostMapping
    @Override
    public User create(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    // Обновление пользователя
    @PutMapping
    @Override
    public User update(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }
}
