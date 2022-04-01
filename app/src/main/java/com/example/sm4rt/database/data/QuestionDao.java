package com.example.sm4rt.database.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface QuestionDao extends BaseDao<Question>{

    @Query("SELECT * FROM Question WHERE id=:id")
    Question findById(int id);

    @Query("SELECT * FROM Question WHERE topic=:topic")
    List<Question> findByTopic(String topic);

    @Query("SELECT * FROM Question WHERE title LIKE :keyword")
    List<Question> findByKeywordContained(String keyword);

    @Query("SELECT * FROM Question")
    List<Question> findAll();

    @Query("SELECT COUNT(*) from Question")
    int count();

    @Query("DELETE FROM Question")
    void deleteAll();


}
