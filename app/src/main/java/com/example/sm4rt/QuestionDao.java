package com.example.sm4rt;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    void insertAll(Question... questions);

    @Update
    void updateQuestions(Question... questions);
    //room uses the primary key to match passed entity instances to rows in the database

    @Delete
    void delete(Question question);

    @Query("SELECT * FROM question")
    List<Question> getAll();

    @Query("SELECT title FROM question")
    List<String> loadTitle();

    @Query("SELECT * FROM question WHERE topic = :topic")
    Question[] loadAllQuestionsForTopic(String topic);

    @Query("SELECT * FROM question WHERE id IN (:questionIds)")
    List<Question> loadAllByIds(int[] questionIds);

    @Query("SELECT * FROM question WHERE title LIKE :search")
    List<Question> findQuestionsContainingKeyword(String search);
}
