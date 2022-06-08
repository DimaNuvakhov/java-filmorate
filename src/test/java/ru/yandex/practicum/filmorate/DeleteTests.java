package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.friends.DbFriendsStorage;
import ru.yandex.practicum.filmorate.storage.genre.DbGenreStorage;
import ru.yandex.practicum.filmorate.storage.genres.DbGenresStorage;
import ru.yandex.practicum.filmorate.storage.likes.DbLikesStorage;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeleteTests {

    private final DbUserStorage dbUserStorage;
    private final DbLikesStorage dbLikesStorage;
    private final DbGenresStorage dbGenresStorage;
    private final DbFilmStorage dbFilmStorage;
    private final DbFriendsStorage dbFriendsStorage;

    @Test
    public void deleteUserTest() {
        // Создаю фильм
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        // Создаю первого пользователя
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        User createdUser = dbUserStorage.createUser(user);
        User getUser = dbUserStorage.getUser(createdUser.getId());
        // Создаю лайк
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(1);
        dbLikesStorage.createLike(like);
        // Создаю второго пользователя
        User secondUser = new User();
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate secondUserBirthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(secondUserBirthday);
        dbUserStorage.createUser(secondUser);
        // Создаю друга
        Friends friends = new Friends();
        friends.setUserId(1);
        friends.setFriendId(2);
        friends.setIsAccepted(false);
        dbFriendsStorage.createFriend(friends);
        // Удаляю пользователя
        assertTrue(dbUserStorage.deleteUser(getUser.getId()));
    }

    @Test
    public void deleteLikeTest() {
        // Создаю фильм
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        // Создаю жанры
        Genres genres = new Genres();
        genres.setGenreId(1);
        genres.setFilmId(1);
        dbGenresStorage.createGenres(genres);
        // Создаю пользователя
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        dbUserStorage.createUser(user);
        // Создаю лайк
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(1);
        Likes createdLike = dbLikesStorage.createLike(like);
        Likes getLike = dbLikesStorage.getLike(createdLike.getId());
        // Удаляю лайк
        dbLikesStorage.deleteLike(getLike.getFilmId(), getLike.getUserId());
    }

    @Test
    public void deleteGenres() {
        // Создаю фильм
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        // Создаю первого пользователя
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        User createdUser = dbUserStorage.createUser(user);
        User getUser = dbUserStorage.getUser(createdUser.getId());
        // Создаю лайк
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(1);
        dbLikesStorage.createLike(like);
        // Создаю второго пользователя
        User secondUser = new User();
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate secondUserBirthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(secondUserBirthday);
        dbUserStorage.createUser(secondUser);
        // Создаю друга
        Friends friends = new Friends();
        friends.setUserId(1);
        friends.setFriendId(2);
        friends.setIsAccepted(false);
        dbFriendsStorage.createFriend(friends);
        // Создаю жанры
        Genres genre = new Genres();
        genre.setFilmId(1);
        genre.setGenreId(1);
        Genres createdGenre = dbGenresStorage.createGenres(genre);
        Genres getGenre = dbGenresStorage.getGenres(createdGenre.getId());
        // Удаляю жанры
        assertTrue(dbGenresStorage.deleteGenres(getGenre.getId()));
    }

    @Test
    public void deleteFriendTest() {
        // Создаю первого пользователя
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        User createdUser = dbUserStorage.createUser(user);
        dbUserStorage.getUser(createdUser.getId());
        // Создаю второго пользователя
        User secondUser = new User();
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate secondUserBirthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(secondUserBirthday);
        dbUserStorage.createUser(secondUser);
        // Создание друга
        Friends friends = new Friends();
        friends.setUserId(1);
        friends.setFriendId(2);
        friends.setIsAccepted(false);
        Friends createdFriend = dbFriendsStorage.createFriend(friends);
        // Удаление друга
        assertTrue(dbFriendsStorage.deleteFriend(createdFriend.getId()));
    }

    @Test
    public void deleteFilmTest() {
        // Создаю фильм
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        Film createdFilm = dbFilmStorage.createFilm(film);
        Film getFilm = dbFilmStorage.getFilm(createdFilm.getId());
        // Создаю первого пользователя
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        dbUserStorage.createUser(user);
        // Создаю лайк
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(1);
        dbLikesStorage.createLike(like);
        // Создаю второго пользователя
        User secondUser = new User();
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate secondUserBirthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(secondUserBirthday);
        dbUserStorage.createUser(secondUser);
        // Создаю друга
        Friends friends = new Friends();
        friends.setUserId(1);
        friends.setFriendId(2);
        friends.setIsAccepted(false);
        dbFriendsStorage.createFriend(friends);
        // Создаю жанры
        Genres genre = new Genres();
        genre.setFilmId(1);
        genre.setGenreId(1);
        Genres createdGenre = dbGenresStorage.createGenres(genre);
        dbGenresStorage.getGenres(createdGenre.getId());
        // Удаляю фильм
        assertTrue(dbFilmStorage.deleteFilm(getFilm.getId()));
    }

}
