package ru.yandex.practicum.filmorate.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller<T>  {

    protected static final Logger log = LoggerFactory.getLogger(FilmController.class);
    protected final Map<Integer, T> items = new HashMap<>();
    protected Integer idMax = 0;

    public Integer getIdMax() {
        idMax = idMax + 1;
        return idMax;
    }

    abstract public Collection<T> getAll();

    abstract public T add(T t);

    abstract public T update(T t);

}
