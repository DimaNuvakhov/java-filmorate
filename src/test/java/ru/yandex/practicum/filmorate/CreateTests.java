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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CreateTests {

    private final DbUserStorage dbUserStorage;
    private final DbLikesStorage dbLikesStorage;
    private final DbGenresStorage dbGenresStorage;
    private final DbFilmStorage dbFilmStorage;
    private final DbFriendsStorage dbFriendsStorage;

    @Test
    public void createUserTest() {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        User createdUser = dbUserStorage.createUser(user);
        // Метод хранилища getUser тестируется в рамках createUserTest() и updateUserTest()
        User getUser = dbUserStorage.getUser(createdUser.getId());
        assertEquals("mail@mail.ru", getUser.getEmail());
        assertEquals("dolore", getUser.getLogin());
        assertEquals("Nick Name", getUser.getName());
        assertEquals(birthday, getUser.getBirthday());
    }

    @Test
    public void createLikeTest() {
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        dbUserStorage.createUser(user);
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(1);
        Likes createdLike = dbLikesStorage.createLike(like);
        Likes getLike = dbLikesStorage.getLike(createdLike.getId());
        assertEquals(1, getLike.getFilmId());
        assertEquals(1, getLike.getUserId());
    }


    @Test
    public void createGenresTest() {
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        Genres genre = new Genres();
        genre.setFilmId(1);
        genre.setGenreId(1);
        Genres createGenre = dbGenresStorage.createGenres(genre);
        Genres getGenre = dbGenresStorage.getGenres(createGenre.getId());
        assertEquals(1, getGenre.getFilmId());
        assertEquals(1, getGenre.getGenreId());
    }

    @Test
    public void createFriendTest() {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        LocalDate birthday = LocalDate.parse("1946-08-20");
        user.setBirthday(birthday);
        dbUserStorage.createUser(user);
        User secondUser = new User();
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate secondUserBirthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(secondUserBirthday);
        dbUserStorage.createUser(secondUser);
        Friends friends = new Friends();
        friends.setUserId(1);
        friends.setFriendId(2);
        friends.setIsAccepted(false);
        Friends createdFriends = dbFriendsStorage.createFriend(friends);
        Friends getFriend = dbFriendsStorage.getFriend(createdFriends.getId());
        assertEquals(1, getFriend.getUserId());
        assertEquals(2, getFriend.getFriendId());
        assertFalse(getFriend.getIsAccepted());
    }

    @Test
    public void createFilmTest() {
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
        assertEquals("labore nulla", getFilm.getName());
        assertEquals(releaseDate, getFilm.getReleaseDate());
        assertEquals("Duis in consequat esse", getFilm.getDescription());
        assertEquals(100, getFilm.getDuration());
    }
}

