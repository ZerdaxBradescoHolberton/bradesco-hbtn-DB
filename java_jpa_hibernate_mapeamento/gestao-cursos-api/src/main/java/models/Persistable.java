package models;

import java.util.List;

public interface Persistable<T> {

    void create(T t);
    void update(T t);
    void delete(T t);
    T findById(T t);
    T findById(Long id);
    List<T> findAll();


}
