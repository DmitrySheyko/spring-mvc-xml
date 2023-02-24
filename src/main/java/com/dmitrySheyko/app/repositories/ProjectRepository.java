package com.dmitrySheyko.app.repositories;

import java.util.List;

public interface ProjectRepository<T> {

    List<T> retrieveAll();

    void store(T book);

    void removeItemById(Integer bookIdToRemove);

    void removeByRegex(String authorForDelete, String titleForDelete, String sizeForDelete);

}
