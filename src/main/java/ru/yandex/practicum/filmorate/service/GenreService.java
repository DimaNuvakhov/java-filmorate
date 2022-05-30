package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

@Service
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre createGenre (Genre genre) {
        return genreStorage.createGenre(genre);
    }

    public Genre updateGenre (Genre genre) {
        return genreStorage.updateGenre(genre);
    }

    public Genre getGenreById (Integer genreId) {
        return genreStorage.getGenre(genreId);
    }

    public Boolean removeGenreById(Integer genreId) {
        return genreStorage.deleteGenre(genreId);
    }

}
