package ru.yandex.practicum.filmorate.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {

//    @Test
//    public void shouldAddFilm() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDescription("Про затонувший кораблю Титаник");
//        film.setReleaseDate(LocalDate.of(1997, 12, 17));
//        film.setDuration(195);
//        filmController.add(film);
//        Collection<Film> films = filmController.getAll();
//        for (Film addedFilm : films) {
//            assertEquals(1, addedFilm.getId());
//            assertEquals("Титаник", addedFilm.getName());
//            assertEquals("Про затонувший кораблю Титаник", addedFilm.getDescription());
//            assertEquals(LocalDate.of(1997, 12, 17), addedFilm.getReleaseDate());
//            assertEquals(195, addedFilm.getDuration());
//        }
//    }
//
//    @Test
//    public void shouldUpdateFilm() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDescription("Про затонувший кораблю Титаник");
//        film.setReleaseDate(LocalDate.of(1997, 12, 17));
//        film.setDuration(195);
//        filmController.add(film);
//        Film secondFilm = new Film();
//        secondFilm.setId(1);
//        secondFilm.setName("Троя");
//        secondFilm.setDescription("Про Троянскую войну");
//        secondFilm.setReleaseDate(LocalDate.of(2004, 5, 5));
//        secondFilm.setDuration(196);
//        filmController.update(secondFilm);
//        Collection<Film> films = filmController.getAll();
//        for (Film addedFilm : films) {
//            assertEquals(1, addedFilm.getId());
//            assertEquals("Троя", addedFilm.getName());
//            assertEquals("Про Троянскую войну", addedFilm.getDescription());
//            assertEquals(LocalDate.of(2004, 5, 5), addedFilm.getReleaseDate());
//            assertEquals(196, addedFilm.getDuration());
//        }
//    }
//
//
//
//    @Test
//    public void shouldThrowExceptionWhenIdNotNull() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setId(3);
//        film.setName("Титаник");
//        film.setDescription("Про затонувший кораблю Титаник");
//        film.setReleaseDate(LocalDate.of(1997, 12, 17));
//        film.setDuration(195);
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        filmController.add(film);
//                    }
//                });
//        assertEquals("Фильм еще не добавлен в базу данных, вы не можете передавать id", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenNameIsBlank() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setName(" ");
//        film.setDescription("Про затонувший кораблю Титаник");
//        film.setReleaseDate(LocalDate.of(1997, 12, 17));
//        film.setDuration(195);
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        filmController.add(film);
//                    }
//                });
//        assertEquals("Название фильма не может быть пустым", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenDescriptionIsTooLong() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDescription("................................................................."+
//                ".................................................................................."+
//                "........................................................");
//        film.setReleaseDate(LocalDate.of(1997, 12, 17));
//        film.setDuration(195);
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        filmController.add(film);
//                    }
//                });
//        assertEquals("Максимальная длина описания — 200 символов", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenReleaseDateIsTooOld() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDescription("Про затонувший кораблю Титаник");
//        film.setReleaseDate(LocalDate.of(1894, 12, 17));
//        film.setDuration(195);
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        filmController.add(film);
//                    }
//                });
//        assertEquals("Дата релиза фильма должна быть не раньше 28 декабря 1895 года", ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenDurationIsNegative() {
//        FilmController filmController = new FilmController();
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDescription("Про затонувший кораблю Титаник");
//        film.setReleaseDate(LocalDate.of(1997, 12, 17));
//        film.setDuration(-195);
//        ValidationException ex = Assertions.assertThrows(
//                ValidationException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        filmController.add(film);
//                    }
//                });
//        assertEquals("Продолжительность фильма не может быть отрицательной", ex.getMessage());
//    }
}
