package by.it_academy.jd2.Mk_JD2_98_23.dao.api;

import java.util.List;

public interface ICRUDService<T, S> {
    List<T> get();

    T get(long id);
    T add (T item);
}

