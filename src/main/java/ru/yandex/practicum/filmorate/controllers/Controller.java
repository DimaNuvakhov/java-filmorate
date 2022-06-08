package ru.yandex.practicum.filmorate.controllers;

import java.util.Collection;

public abstract class Controller<T>  {

    abstract public Collection<T> getAll();

    abstract public T create(T t);

    abstract public T update(T t);
}
