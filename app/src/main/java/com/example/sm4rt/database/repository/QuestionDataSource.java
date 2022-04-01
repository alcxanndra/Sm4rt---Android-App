package com.example.sm4rt.database.repository;

import com.example.sm4rt.database.data.Question;
import com.example.sm4rt.database.data.QuestionDao;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import javax.inject.Inject;

public class QuestionDataSource implements QuestionRepository {

    private QuestionDao questionDao;

    @Inject
    public QuestionDataSource(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question findById(int id) {
        return questionDao.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionDao.findAll();
    }

    @Override
    public List<Question> findByTopic(String topic) {
        return questionDao.findByTopic(topic);
    }

    @Override
    public List<Question> findByKeywordContained(String keyword) {
        return questionDao.findByKeywordContained(keyword);
    }

    @Override
    public Long insert(Question... questions) {
        questionDao.insert(questions);
        return Long.valueOf(questions.length);
    }

    @Override
    public void delete(Question question) {
        questionDao.delete(question);
    }

    @Override
    public int count(){
        return questionDao.count();
    }

    @Override
    public void deleteAll(){
        questionDao.deleteAll();
    }
}
