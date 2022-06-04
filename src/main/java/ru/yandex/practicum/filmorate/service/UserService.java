package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public User createUser(User user) {
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
        if (userStorage.getAllUsers().values().contains(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с такой электронной почтой уже добавлен в систему");
        }
        if (userStorage.getAllUsers().values().contains(user.getLogin())) {
            throw new UserAlreadyExistException("Пользователь с таким логином уже добавлен в систему");
        }
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
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
        return userStorage.updateUser(user);
    }

    public Collection<User> getAllUsers() {
        log.debug("Текущее количество добавленных пользователей: {}", userStorage.getAllUsers().size());
        return userStorage.getAllUsers().values();
    }

    public User getUserById(Integer id) {
        if (!userStorage.getAllUsers().containsKey(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему", id));
        }
        return userStorage.getUser(id);
    }

    public Boolean addAsFriend(Integer id, Integer friendId) {
        if (!userStorage.getAllUsers().containsKey(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему", id));
        }
        if (!userStorage.getAllUsers().containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему", friendId));
        }
        if (id.equals(friendId)) {
            throw new IllegalAddAsFriendException("Пользователь не может добавть в друзья сам себя");
        }
        Friends friend = new Friends();
        friend.setUserId(id);
        friend.setFriendId(friendId);
        friend.setIsAccepted(false);
        friendsStorage.createFriend(friend);
        return true;
    }

    public Boolean removeFromFriends(Integer id, Integer friendId) {
        if (!userStorage.getAllUsers().containsKey(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему", id));
        }
        if (!userStorage.getAllUsers().containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не добавлен в систему", friendId));
        }
        friendsStorage.removeFromFriends(id, friendId);
        return true;
    }

    public List<User> getUserFriends(Integer id) {
        return userStorage.getAllUsers().get(id).getMyFriends().values().stream()
                .map(u -> userStorage.getAllUsers().get(u.getFriendId()))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        return friendsStorage.getCommonFriends(id, otherId);
    }

    public Boolean removeUserById(Integer userId) {
        return userStorage.deleteUser(userId);
    }
}
