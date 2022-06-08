package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;

@Service
public class GenresService {

    private final GenresStorage genresStorage;

    @Autowired
    public GenresService(GenresStorage genresStorage) {
        this.genresStorage = genresStorage;
    }

    public Genres createGenres(Genres genres) {
        return genresStorage.createGenres(genres);
    }

    public Genres updateGenres(Genres genres) {
        return genresStorage.updateGenres(genres);
    }

    public Genres getGenresById(Integer genresId) {
        return genresStorage.getGenres(genresId);
    }

    public Boolean removeGenresById(Integer genresId) {
        return genresStorage.deleteGenres(genresId);
    }

}
