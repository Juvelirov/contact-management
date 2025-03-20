package ru.denisov.NauJava.Repositories;

public interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id);
    T read(String number);
    void update(T entity);
    void delete(ID id);
}
