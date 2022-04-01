package com.example.sm4rt.database.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface TopicDao extends BaseDao<Topic> {

    @Query("SELECT * FROM Topic WHERE id=:id")
    Topic findById(int id);

    @Query("SELECT * FROM Topic WHERE name=:name")
    Topic findByName(String name);

    @Query("SELECT * FROM Topic")
    List<Topic> findAll();

    @Query("SELECT COUNT(*) from Topic")
    int count();

    @Query("DELETE FROM Topic")
    void deleteAll();

}
