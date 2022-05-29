package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;

@Service
public class GenreService {

    private final GenreService genreService;

    @Autowired
    public GenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    public Genre createGenre (Genre genre) {
        return genreService.createGenre(genre);
    }

    public Genre updateGenre (Genre genre) {
        return genreService.createGenre(genre);
    }

    public Genre getGenreById (Integer genreId) {
        return genreService.getGenreById(genreId);
    }

    public Boolean removeGenreById(Integer genreId) {
        return genreService.removeGenreById(genreId);
    }

}
