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
import ru.yandex.practicum.filmorate.storage.rating.DbRatingStorage;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmorateApplicationTests {

    private final DbUserStorage dbUserStorage;
    private final DbLikesStorage dbLikesStorage;
    private final DbGenresStorage dbGenresStorage;
    private final DbGenreStorage dbGenreStorage;
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
    public void deleteUserTest() {
        User firstUser = new User();
        firstUser.setEmail("mail@mail.ru");
        firstUser.setLogin("dolore");
        firstUser.setName("Nick Name");
        LocalDate firstUserBirthday = LocalDate.parse("1946-08-20");
        firstUser.setBirthday(firstUserBirthday);
        User createdUser = dbUserStorage.createUser(firstUser);
        assertTrue(dbUserStorage.deleteUser(createdUser.getId()));
    }

    @Test
    public void getAllUsersTest() {
        User firstUser = new User();
        firstUser.setEmail("mail@mail.ru");
        firstUser.setLogin("dolore");
        firstUser.setName("Nick Name");
        LocalDate firstUserBirthday = LocalDate.parse("1946-08-20");
        firstUser.setBirthday(firstUserBirthday);
        User firstCreatedFilm = dbUserStorage.createUser(firstUser);
        User secondUser = new User();
        secondUser.setId(1);
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate birthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(birthday);
        User secondCreatedFilm = dbUserStorage.createUser(secondUser);
        for (User user : dbUserStorage.getAllUsers().values()) {
            if (user.getId().equals(firstCreatedFilm.getId())) {
                assertEquals("mail@mail.ru", user.getEmail());
                assertEquals("dolore", user.getLogin());
                assertEquals("Nick Name", user.getName());
                assertEquals(firstUserBirthday, user.getBirthday());
            } else if (user.getId().equals(secondCreatedFilm.getId())) {
                assertEquals("mail@yandex.ru", user.getEmail());
                assertEquals("doloreUpdate", user.getLogin());
                assertEquals("est adipisicing", user.getName());
                assertEquals(birthday, user.getBirthday());
            }
        }
    }

    @Test
    public void createLikeTest() {
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(2);
        Likes createdLike = dbLikesStorage.createLike(like);
        Likes getLike = dbLikesStorage.getLike(createdLike.getId());
        assertEquals(1, getLike.getFilmId());
        assertEquals(2, getLike.getUserId());
    }

    @Test
    public void updateLikeTest() {
        Likes firstLike = new Likes();
        firstLike.setFilmId(1);
        firstLike.setUserId(2);
        Likes createdLike = dbLikesStorage.createLike(firstLike);
        Likes secondLike = new Likes();
        secondLike.setId(createdLike.getId());
        secondLike.setFilmId(3);
        secondLike.setUserId(4);
        Likes updatedLike = dbLikesStorage.updateLike(secondLike);
        Likes getLike = dbLikesStorage.getLike(updatedLike.getId());
        assertEquals(3, getLike.getFilmId());
        assertEquals(4, getLike.getUserId());
    }

    @Test
    public void deleteLikeTest() {
        Likes like = new Likes();
        like.setFilmId(1);
        like.setUserId(2);
        Likes createdLike = dbLikesStorage.createLike(like);
        dbLikesStorage.deleteLike(createdLike.getFilmId(), createdLike.getUserId());
    }

    @Test
    public void createGenresTest() {
        Genres genre = new Genres();
        genre.setFilmId(1);
        genre.setGenreId(1);
        Genres createGenre = dbGenresStorage.createGenres(genre);
        Genres getGenre = dbGenresStorage.getGenres(createGenre.getId());
        assertEquals(1, getGenre.getFilmId());
        assertEquals(1, getGenre.getGenreId());
    }

    @Test
    public void updateGenres() {
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
    public void deleteGenres() {
        Genres genre = new Genres();
        genre.setFilmId(1);
        genre.setGenreId(1);
        Genres createdGenre = dbGenresStorage.createGenres(genre);
        assertTrue(dbGenresStorage.deleteGenres(createdGenre.getId()));
    }

    @Test
    public void createGenre() {
        Genre genre = new Genre();
        genre.setValue("DRAMA");
        genre.setComment("Драма");
        Genre createdGenre = dbGenreStorage.createGenre(genre);
        Genre getGenre = dbGenreStorage.getGenre(createdGenre.getId());
        assertEquals("DRAMA", getGenre.getValue());
        assertEquals("Драма", getGenre.getComment());
    }

    @Test
    public void updateGenreTest() {
        Genre firstGenre = new Genre();
        firstGenre.setValue("DRAMA");
        firstGenre.setComment("Драма");
        Genre createdGenre = dbGenreStorage.createGenre(firstGenre);
        Genre secondGenre = new Genre();
        secondGenre.setId(createdGenre.getId());
        secondGenre.setValue("CARTOON");
        secondGenre.setComment("Мультфильм");
        Genre updatedGenre = dbGenreStorage.updateGenre(secondGenre);
        Genre getGenre = dbGenreStorage.getGenre(updatedGenre.getId());
        assertEquals("CARTOON", getGenre.getValue());
        assertEquals("Мультфильм", getGenre.getComment());
    }

    @Test
    public void deleteGenreTest() {
        Genre genre = new Genre();
        genre.setValue("DRAMA");
        genre.setComment("Драма");
        Genre createdGenre = dbGenreStorage.createGenre(genre);
        assertTrue(dbGenreStorage.deleteGenre(createdGenre.getId()));
    }

    @Test
    public void createFriendTest() {
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
    public void updateFriendTest() {
        Friends firstFriends = new Friends();
        firstFriends.setUserId(1);
        firstFriends.setFriendId(2);
        firstFriends.setIsAccepted(false);
        Friends createdFriend = dbFriendsStorage.createFriend(firstFriends);
        Friends secondFriends = new Friends();
        secondFriends.setId(createdFriend.getId());
        secondFriends.setUserId(2);
        secondFriends.setFriendId(3);
        secondFriends.setIsAccepted(false);
        Friends updatedFriends = dbFriendsStorage.updateFriend(secondFriends);
        Friends getFriend = dbFriendsStorage.getFriend(updatedFriends.getId());
        assertEquals(2, getFriend.getUserId());
        assertEquals(3, getFriend.getFriendId());
        assertFalse(getFriend.getIsAccepted());
    }

    @Test
    public void deleteFriendTest() {
        Friends friends = new Friends();
        friends.setUserId(1);
        friends.setFriendId(2);
        friends.setIsAccepted(false);
        Friends createdFriend = dbFriendsStorage.createFriend(friends);
        assertTrue(dbFriendsStorage.deleteFriend(createdFriend.getId()));
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

    @Test
    public void deleteFilmTest() {
        Film film = new Film();
        film.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        film.setReleaseDate(releaseDate);
        film.setDescription("Duis in consequat esse");
        film.setDuration(100);
        Rating mpa = new Rating();
        film.setMpa(mpa);
        Film createdFilm = dbFilmStorage.createFilm(film);
        assertTrue(dbFilmStorage.deleteFilm(createdFilm.getId()));
    }

    @Test
    public void getAllFilmsTest() {
        Film firstFilm = new Film();
        firstFilm.setName("labore nulla");
        LocalDate releaseDate = LocalDate.parse("1979-04-17");
        firstFilm.setReleaseDate(releaseDate);
        firstFilm.setDescription("Duis in consequat esse");
        firstFilm.setDuration(100);
        Rating mpa = new Rating();
        firstFilm.setMpa(mpa);
        Film firstCreatedFilm = dbFilmStorage.createFilm(firstFilm);
        Film secondFilm = new Film();
        secondFilm.setName("New film");
        LocalDate secondFilmReleaseDate = LocalDate.parse("1999-04-30");
        secondFilm.setReleaseDate(secondFilmReleaseDate);
        secondFilm.setDescription("New film about friends");
        secondFilm.setDuration(120);
        Rating secondMpa = new Rating();
        secondFilm.setMpa(secondMpa);
        Film secondCreatedFilm = dbFilmStorage.createFilm(secondFilm);
        for (Film film : dbFilmStorage.getAllFilms().values()) {
            if (film.getId().equals(firstCreatedFilm.getId())) {
                assertEquals("labore nulla", film.getName());
                assertEquals(releaseDate, film.getReleaseDate());
                assertEquals("Duis in consequat esse", film.getDescription());
                assertEquals(100, film.getDuration());
            } else if (film.getId().equals(secondCreatedFilm.getId())) {
                assertEquals("New film", film.getName());
                assertEquals(secondFilmReleaseDate, film.getReleaseDate());
                assertEquals("New film about friends", film.getDescription());
                assertEquals(120, film.getDuration());
            }
        }
    }
}

