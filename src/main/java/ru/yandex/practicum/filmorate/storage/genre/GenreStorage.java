package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Map;

public interface GenreStorage {
    Genre createGenre(Genre genre);

    Genre updateGenre(Genre genre);

    Boolean deleteGenre(Integer genreId);

    Genre getGenre(Integer genreId);
}
