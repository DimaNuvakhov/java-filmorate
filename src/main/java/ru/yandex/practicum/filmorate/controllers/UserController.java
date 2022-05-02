package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

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

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // TODO Доделать
    @PutMapping("/{id}/friends/{friendId}")
    public User addAsFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        return null;
    }

    // TODO Доделать
    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFromFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        return null;
    }

    // TODO Доделать
    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable Integer id) {
        return null;
    }

    // TODO Доделать
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUsersCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return null;
    }
}
