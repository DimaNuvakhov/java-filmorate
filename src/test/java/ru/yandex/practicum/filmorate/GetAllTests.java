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
class GetAllTests {

    private final DbUserStorage dbUserStorage;
    private final DbFilmStorage dbFilmStorage;

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

