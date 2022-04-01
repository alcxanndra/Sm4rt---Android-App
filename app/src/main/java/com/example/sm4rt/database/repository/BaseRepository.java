package com.example.sm4rt.database.repository;

import com.example.sm4rt.database.data.Question;

import java.util.List;

public interface BaseRepository<T> {

    T findById(int id);

    List<T> findAll();

    Long insert(T... t);

    void delete(T t);
}
