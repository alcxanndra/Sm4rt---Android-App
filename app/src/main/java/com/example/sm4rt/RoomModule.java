package com.example.sm4rt;

import android.app.Application;

import androidx.room.Room;

import com.example.sm4rt.database.data.QuestionDao;
import com.example.sm4rt.database.data.TopicDao;
import com.example.sm4rt.database.repository.AppDatabase;
import com.example.sm4rt.database.repository.QuestionDataSource;
import com.example.sm4rt.database.repository.QuestionRepository;
import com.example.sm4rt.database.repository.TopicDataSource;
import com.example.sm4rt.database.repository.TopicRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppDatabase appDatabase;

    public RoomModule(Application mApplication) {
        appDatabase = Room.databaseBuilder(mApplication, AppDatabase.class, "sm4rt-db").build();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    TopicDao providesTopicDao(AppDatabase appDatabase) {
        return appDatabase.getTopicDao();
    }

    @Singleton
    @Provides
    TopicRepository topicRepository(TopicDao topicDao) {
        return new TopicDataSource(topicDao);
    }

    @Singleton
    @Provides
    QuestionDao providesQuestionDao(AppDatabase appDatabase) {
        return appDatabase.getQuestionDao();
    }

    @Singleton
    @Provides
    QuestionRepository questionRepository(QuestionDao questionDao) {
        return new QuestionDataSource(questionDao);
    }

}