package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

public class User {
    Integer id;
    String email;
    String login;
    String name;
    LocalDate birthDate;

    public User(String email, String login, String name, LocalDate birthDate) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
