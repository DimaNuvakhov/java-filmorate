package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Likes {
    private Integer id;
    private Integer filmId;
    private Integer userId;
}
