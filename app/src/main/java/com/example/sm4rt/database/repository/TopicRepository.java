package com.example.sm4rt.database.repository;

import androidx.lifecycle.LiveData;

import com.example.sm4rt.database.data.Topic;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public interface TopicRepository extends BaseRepository<Topic>{
    Topic findByName(String name);
    int count();
    void deleteAll();
}