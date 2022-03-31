package com.example.sm4rt;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TopicDao {

    @Insert
    void insertAll(Topic... topics);

    @Update
    void updateTopics(Topic... topics);
    //room uses the primary key to match passed entity instances to rows in the database

    @Delete
    void delete(Topic topic);

    @Query("SELECT * FROM topic")
    List<Topic> getAll();

    @Query("SELECT name FROM topic")
    List<String> loadName();

    @Query("SELECT * FROM topic WHERE description = :description")
    Topic findTopicByDescription(String description);

    @Query("SELECT * FROM topic WHERE id IN (:topicIds)")
    List<Topic> loadAllByIds(int[] topicIds);

    @Query("SELECT * FROM topic WHERE name LIKE :search LIMIT 1")
    Topic findTopicByName(String search);
}
