package ru.yandex.practicum.filmorate.controllers;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller<T>  {

    private final Map<Integer, T> items = new HashMap<>();
    private Integer idMax = 0;

    public Integer getIdMax() {
        idMax++;
        return idMax;
    }

    Collection<T> getAll() {
        return items.values();
    }

    public T add(T t) {
        return t;
    }

    public T update(T t) {
        return t;
    }

}
