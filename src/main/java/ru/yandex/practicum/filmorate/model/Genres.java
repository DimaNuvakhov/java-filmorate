package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Genres {
    private Integer id;
    private Integer filmId;
    private Integer genreId;
    private Map<Integer, Genre> genre = new HashMap<>();
}
