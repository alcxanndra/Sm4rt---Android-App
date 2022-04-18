package com.example.sm4rt.database.repository;

import androidx.lifecycle.LiveData;

import com.example.sm4rt.database.data.Topic;

import java.util.List;

public interface TopicRepository extends BaseRepository<Topic>{
    Topic findByName(String name);
    int count();
    void deleteAll();
}