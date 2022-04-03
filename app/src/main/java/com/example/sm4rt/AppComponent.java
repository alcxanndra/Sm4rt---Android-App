package com.example.sm4rt;

import android.app.Application;
import android.content.Context;

import com.example.sm4rt.activity.MainActivity;
import com.example.sm4rt.activity.QuestionsPage;
import com.example.sm4rt.activity.RandomQuestionActivity;
import com.example.sm4rt.activity.TopicsPage;
import com.example.sm4rt.database.data.QuestionDao;
import com.example.sm4rt.database.data.TopicDao;
import com.example.sm4rt.database.repository.AppDatabase;
import com.example.sm4rt.database.repository.QuestionRepository;
import com.example.sm4rt.database.repository.TopicRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(TopicsPage topicsPage);

    void inject(QuestionsPage questionsPage);

    void inject(RandomQuestionActivity randomQuestionActivity);

    TopicDao topicDao();

    QuestionDao questionDao();

    AppDatabase appDatabase();

    TopicRepository topicRepository();

    QuestionRepository questionRepository();

    Application application();

}