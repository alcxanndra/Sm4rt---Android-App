package com.example.sm4rt.database.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface BaseDao<T>  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... t);

    @Delete
    void delete(T t);

}
