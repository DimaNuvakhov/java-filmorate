package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Genres {
    private Integer id;
    private Integer filmId;
    private Integer genreId;
    private List<Genre> genre = new ArrayList<>();
}
