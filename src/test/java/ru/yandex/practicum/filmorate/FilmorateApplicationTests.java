package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.DbGenreStorage;
import ru.yandex.practicum.filmorate.storage.genres.DbGenresStorage;
import ru.yandex.practicum.filmorate.storage.likes.DbLikesStorage;
import ru.yandex.practicum.filmorate.storage.rating.DbRatingStorage;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmorateApplicationTests {

    private final DbUserStorage dbUserStorage;
    private final DbRatingStorage dbRatingStorage;
    private final DbLikesStorage dbLikesStorage;
    private final DbGenresStorage dbGenresStorage;

    private final DbGenreStorage dbGenreStorage;
    private final DbFilmStorage dbFilmStorage;

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
        dbUserStorage.createUser(firstUser);
        User secondUser = new User();
        secondUser.setId(1);
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
        dbUserStorage.createUser(firstUser);
        User secondUser = new User();
        secondUser.setId(1);
        secondUser.setEmail("mail@yandex.ru");
        secondUser.setLogin("doloreUpdate");
        secondUser.setName("est adipisicing");
        LocalDate birthday = LocalDate.parse("1976-09-20");
        secondUser.setBirthday(birthday);
        dbUserStorage.createUser(secondUser);
        for (User user : dbUserStorage.getAllUsers().values()) {
            if (user.getId().equals(firstUser.getId())) {
                assertEquals("mail@mail.ru", user.getEmail());
                assertEquals("dolore", user.getLogin());
                assertEquals("Nick Name", user.getName());
                assertEquals(firstUserBirthday, user.getBirthday());
            } else if (user.getId().equals(secondUser.getId())) {
                assertEquals("mail@yandex.ru", user.getEmail());
                assertEquals("doloreUpdate", user.getLogin());
                assertEquals("est adipisicing", user.getName());
                assertEquals(birthday, user.getBirthday());
            }
        }
    }

    @Test
    public void createRatingTest() {
        Rating rating = new Rating();
        rating.setValue("G");
        rating.setComment("У фильма нет возрастных ограничений");
        Rating createdRating = dbRatingStorage.createRating(rating);
        Rating getRating = dbRatingStorage.getRating(createdRating.getId());
        assertEquals("G", getRating.getValue());
        assertEquals("У фильма нет возрастных ограничений", getRating.getComment());
    }

    @Test
    public void updateRatingTest() {
        Rating firstRating = new Rating();
        firstRating.setValue("G");
        firstRating.setComment("У фильма нет возрастных ограничений");
        dbRatingStorage.createRating(firstRating);
        Rating secondRating = new Rating();
        secondRating.setId(1);
        secondRating.setValue("PG");
        secondRating.setComment("Детям рекомендуется смотреть фильм с родителями");
        Rating updatedRating = dbRatingStorage.updateRating(secondRating);
        Rating getRating = dbRatingStorage.getRating(updatedRating.getId());
        assertEquals("PG", getRating.getValue());
        assertEquals("Детям рекомендуется смотреть фильм с родителями", getRating.getComment());
    }

    @Test
    public void deleteRatingTest() {
        Rating rating = new Rating();
        rating.setValue("G");
        rating.setComment("У фильма нет возрастных ограничений");
        Rating createdRating = dbRatingStorage.createRating(rating);
        assertTrue(dbRatingStorage.deleteRating(createdRating.getId()));
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
        dbLikesStorage.createLike(firstLike);
        Likes secondLike = new Likes();
        secondLike.setId(1);
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
        dbGenresStorage.createGenres(firstGenre);
        Genres secondGenre = new Genres();
        secondGenre.setId(1);
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

//    @Test
//    public void createFilmTest() {
//        Film film = new Film();
//        film.setName("labore nulla");
//        LocalDate releaseDate = LocalDate.parse("1979-04-17");
//        film.setReleaseDate(releaseDate);
//        film.setDescription("Duis in consequat esse");
//        film.setDuration(100);
//        film.getMpa().setId(1);
//        Film createdFilm = dbFilmStorage.createFilm(film);
//        Film getFilm = dbFilmStorage.getFilm(createdFilm.getId());
//        assertEquals("labore nulla", );
//        assertEquals(releaseDate, );
//        assertEquals("Duis in consequat esse", );
//        assertEquals(100);
//        assertEquals();
//    }

}
