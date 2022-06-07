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
class UpdateTests {

    private final DbUserStorage dbUserStorage;
    private final DbLikesStorage dbLikesStorage;
    private final DbGenresStorage dbGenresStorage;
    private final DbFilmStorage dbFilmStorage;
    private final DbFriendsStorage dbFriendsStorage;

    @Test
    public void updateUserTest() {
        User firstUser = new User();
        firstUser.setEmail("mail@mail.ru");
        firstUser.setLogin("dolore");
        firstUser.setName("Nick Name");
        LocalDate firstUserBirthday = LocalDate.parse("1946-08-20");
        firstUser.setBirthday(firstUserBirthday);
        User createdUser = dbUserStorage.createUser(firstUser);
        User secondUser = new User();
        secondUser.setId(createdUser.getId());
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate birthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(birthday);
        User updatedUser = dbUserStorage.updateUser(secondUser);
        User getUser = dbUserStorage.getUser(updatedUser.getId());
        assertEquals("mail@yandex.ru", getUser.getEmail());
        assertEquals("doloreUpdate", getUser.getLogin());
        assertEquals("est adipisicing", getUser.getName());
        assertEquals(birthday, getUser.getBirthday());
    }

    @Test
    public void updateLikeTest() {
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
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        Film secondFilm = new Film();
        secondFilm.setName("Film Updated");
        LocalDate secondFilmReleaseDate = LocalDate.parse("1989-04-17");
        secondFilm.setReleaseDate(secondFilmReleaseDate);
        secondFilm.setDescription("New film update decription");
        secondFilm.setDuration(190);
        Rating secondMpa = new Rating();
        secondFilm.setMpa(secondMpa);
        dbFilmStorage.createFilm(secondFilm);
        Likes firstLike = new Likes();
        firstLike.setFilmId(1);
        firstLike.setUserId(1);
        Likes createdLike = dbLikesStorage.createLike(firstLike);
        Likes secondLike = new Likes();
        secondLike.setId(createdLike.getId());
        secondLike.setFilmId(2);
        secondLike.setUserId(2);
        Likes updatedLike = dbLikesStorage.updateLike(secondLike);
        Likes getLike = dbLikesStorage.getLike(updatedLike.getId());
        assertEquals(2, getLike.getFilmId());
        assertEquals(2, getLike.getUserId());
    }

    @Test
    public void updateGenresTest() {
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        dbFilmStorage.createFilm(film);
        Film secondFilm = new Film();
        secondFilm.setName("Film Updated");
        LocalDate secondFilmReleaseDate = LocalDate.parse("1989-04-17");
        secondFilm.setReleaseDate(secondFilmReleaseDate);
        secondFilm.setDescription("New film update decription");
        secondFilm.setDuration(190);
        Rating secondMpa = new Rating();
        secondFilm.setMpa(secondMpa);
        dbFilmStorage.createFilm(secondFilm);
        Genres firstGenre = new Genres();
        firstGenre.setFilmId(1);
        firstGenre.setGenreId(1);
        Genres createdGenre = dbGenresStorage.createGenres(firstGenre);
        Genres secondGenre = new Genres();
        secondGenre.setId(createdGenre.getId());
        secondGenre.setFilmId(2);
        secondGenre.setGenreId(3);
        Genres updatedGenre = dbGenresStorage.updateGenres(secondGenre);
        Genres getGenre = dbGenresStorage.getGenres(updatedGenre.getId());
        assertEquals(2, getGenre.getFilmId());
        assertEquals(3, getGenre.getGenreId());
    }

    @Test
    public void updateFriendTest() {
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
        User thirdUser = new User();
        thirdUser.setEmail("thirdusermail@yandex.ru");
        thirdUser.setLogin("thirdUser");
        thirdUser.setName("ThirdUserName");
        LocalDate thirdUserBirthday = LocalDate.parse("1976-09-20");
        thirdUser.setBirthday(thirdUserBirthday);
        dbUserStorage.createUser(thirdUser);
        Friends firstFriends = new Friends();
        firstFriends.setUserId(1);
        firstFriends.setFriendId(2);
        firstFriends.setIsAccepted(false);
        Friends createdFriend = dbFriendsStorage.createFriend(firstFriends);
        Friends secondFriends = new Friends();
        secondFriends.setId(createdFriend.getId());
        secondFriends.setUserId(1);
        secondFriends.setFriendId(3);
        secondFriends.setIsAccepted(false);
        Friends updatedFriends = dbFriendsStorage.updateFriend(secondFriends);
        Friends getFriend = dbFriendsStorage.getFriend(updatedFriends.getId());
        assertEquals(1, getFriend.getUserId());
        assertEquals(3, getFriend.getFriendId());
        assertFalse(getFriend.getIsAccepted());
    }

    @Test
    public void updateFilmTest() {
        Film firstFilm = new Film();
        firstFilm.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        firstFilm.setReleaseDate(releaseDate);
        firstFilm.setDescription("Duis in consequat esse");
        firstFilm.setDuration(100);
        Rating mpa = new Rating();
        firstFilm.setMpa(mpa);
        mpa.setId(1);
        Film createdFilm = dbFilmStorage.createFilm(firstFilm);
        Film secondFilm = new Film();
        secondFilm.setId(createdFilm.getId());
        secondFilm.setName("Film Updated");
        LocalDate secondFilmReleaseDate = LocalDate.parse("1989-04-17");
        secondFilm.setReleaseDate(secondFilmReleaseDate);
        secondFilm.setDescription("New film update decription");
        secondFilm.setDuration(190);
        Rating secondMpa = new Rating();
        secondMpa.setId(2);
        secondFilm.setMpa(secondMpa);
        Film updatedFilm = dbFilmStorage.updateFilm(secondFilm);
        Film getFilm = dbFilmStorage.getFilm(updatedFilm.getId());
        assertEquals("Film Updated", getFilm.getName());
        assertEquals(secondFilmReleaseDate, getFilm.getReleaseDate());
        assertEquals("New film update decription", getFilm.getDescription());
        assertEquals(190, getFilm.getDuration());
    }
}

