package ru.yandex.practicum.filmorate.storage.genres;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.Map;

public interface GenresStorage {

    Genres createGenres(Genres genres);

    Genres updateGenres(Genres genres);

    Boolean deleteGenres(Integer genresId);

    Genres getGenres(Integer genresId);
}
