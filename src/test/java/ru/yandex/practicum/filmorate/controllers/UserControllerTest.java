package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

//    @Test
//    public void shouldAddUser() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail("mail@mail.ru");
//        user.setLogin("TrueJedi");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        userController.add(user);
//        assertEquals(1, user.getId());
//        assertEquals("mail@mail.ru", user.getEmail());
//        assertEquals("TrueJedi", user.getLogin());
//        assertEquals("Дима", user.getName());
//        assertEquals(LocalDate.of(1994, 3, 23), user.getBirthday());
//        Collection<User> users = userController.getAll();
//        for (User addedUser : users) {
//            assertEquals(1, addedUser.getId());
//            assertEquals("mail@mail.ru", addedUser.getEmail());
//            assertEquals("TrueJedi", addedUser.getLogin());
//            assertEquals("Дима", addedUser.getName());
//            assertEquals(LocalDate.of(1994, 3, 23), addedUser.getBirthday());
//        }
//    }
//
//    @Test
//    public void shouldUpdateUser() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail("mail@mail.ru");
//        user.setLogin("TrueJedi");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        userController.add(user);
//        User secondUser = new User();
//        secondUser.setId(1);
//        secondUser.setEmail("Dima@mail.ru");
//        secondUser.setLogin("Yoda");
//        secondUser.setName("Дима");
//        secondUser.setBirthday(LocalDate.of(1994, 3, 23));
//        userController.update(secondUser);
//        Collection<User> users = userController.getAll();
//        for (User addedUser : users) {
//            assertEquals(1, addedUser.getId());
//            assertEquals("Dima@mail.ru", addedUser.getEmail());
//            assertEquals("Yoda", addedUser.getLogin());
//            assertEquals("Дима", addedUser.getName());
//            assertEquals(LocalDate.of(1994, 3, 23), addedUser.getBirthday());
//        }
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenIdNotNull() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setId(3);
//        user.setEmail("mail@mail.ru");
//        user.setLogin("TrueJedi");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        userController.add(user);
//                    }
//                });
//        assertEquals("Пользователь еще не добавлен в базу данных, вы не можете передавать id", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenMailIsEmpty() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail(" ");
//        user.setLogin("TrueJedi");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        userController.add(user);
//                    }
//                });
//        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenMailWithoutSymbol() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail("mailmail.ru");
//        user.setLogin("TrueJedi");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        userController.add(user);
//                    }
//                });
//        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenLoginIsBlank() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail("mail@mail.ru");
//        user.setLogin(" ");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        userController.add(user);
//                    }
//                });
//        assertEquals("Логин не может быть пустым и содержать пробелы", ex.getMessage());
//    }
//
//    @Test
//    public void shouldSetLoginAsNameWhenNameIsBlank() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail("mail@mail.ru");
//        user.setLogin("TrueJedi");
//        user.setName(" ");
//        user.setBirthday(LocalDate.of(1994, 3, 23));
//        userController.add(user);
//        assertEquals("TrueJedi", user.getName());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenBirthDateInFuture() {
//        UserController userController = new UserController();
//        User user = new User();
//        user.setEmail("mail@mail.ru");
//        user.setLogin("TrueJedi");
//        user.setName("Дима");
//        user.setBirthday(LocalDate.of(2024, 4, 23));
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        userController.add(user);
//                    }
//                });
//        assertEquals("Дата рождения пользователя не может быть в будущем", ex.getMessage());
//    }
}
