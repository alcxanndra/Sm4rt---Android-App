package com.example.sm4rt.database.repository;

import com.example.sm4rt.database.data.Topic;
import com.example.sm4rt.database.data.TopicDao;

import java.util.List;

import javax.inject.Inject;

public class TopicDataSource implements TopicRepository {

    private TopicDao topicDao;

    @Inject
    public TopicDataSource(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    public Topic findById(int id) {
        return topicDao.findById(id);
    }

    @Override
    public List<Topic> findAll() {
        return topicDao.findAll();
    }

    @Override
    public Topic findByName(String name) {
        return topicDao.findByName(name);
    }

    @Override
    public Long insert(Topic... topics) {
        topicDao.insert(topics);
        return Long.valueOf(topics.length);
    }

    @Override
    public void delete(Topic topic) {
        topicDao.delete(topic);
    }

    @Override
    public int count(){
        return topicDao.count();
    }

    @Override
    public void deleteAll(){
        topicDao.deleteAll();
    }
}
