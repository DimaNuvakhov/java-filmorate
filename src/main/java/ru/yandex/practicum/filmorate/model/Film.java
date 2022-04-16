package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

public class Film {
    Integer id;
    String name;
    String description;
    LocalDate releaseDate;
    Integer filmDuration;

    public Film(String name, String description, LocalDate releaseDate, Integer filmDuration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.filmDuration = filmDuration;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Integer getFilmDuration() {
        return filmDuration;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setFilmDuration(Integer filmDuration) {
        this.filmDuration = filmDuration;
    }
}
