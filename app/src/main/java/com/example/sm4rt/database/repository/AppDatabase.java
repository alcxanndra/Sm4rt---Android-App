package com.example.sm4rt.database.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sm4rt.database.data.QuestionDao;
import com.example.sm4rt.database.data.TopicDao;
import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.Topic;

@Database(entities = {Topic.class, Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TopicDao getTopicDao();
    public abstract QuestionDao getQuestionDao();

}