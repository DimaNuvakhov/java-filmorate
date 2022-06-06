package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.*;

@Data
public class Film {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private List<Likes> filmLikes = new ArrayList<>();
    private Integer ratingId;
    private Rating mpa;
    private List<Genres> genres = new ArrayList<>();
}
