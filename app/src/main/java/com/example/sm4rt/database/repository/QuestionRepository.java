package com.example.sm4rt.database.repository;

import com.example.sm4rt.database.data.Question;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public interface QuestionRepository extends BaseRepository<Question> {
    List<Question> findByTopic(String topic);
    List<Question> findByKeywordContained(String keyword);
    int count();
    void deleteAll();
}
