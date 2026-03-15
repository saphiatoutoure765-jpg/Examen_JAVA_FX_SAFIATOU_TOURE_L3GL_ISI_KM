package com.gl.gestionclinique.Repository;

import java.util.List;

public interface GenericDAO<T> {
    void save(T entite);
    void update(T entite);
    void delete(T entite);
    List<T> getAll();
}
