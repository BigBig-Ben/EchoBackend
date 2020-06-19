package org.demo.service;

import org.demo.DAO.DateDiscussionDAO;
import org.demo.Entity.DateDiscussion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DateDiscussionService {
    @Autowired
    private DateDiscussionDAO dateDiscussionDAO;

    @Transactional
    public void save(DateDiscussion dataComment) {
        dateDiscussionDAO.save(dataComment);
    }

    @Transactional
    public void update(DateDiscussion dateDiscussion) {
        dateDiscussionDAO.save(dateDiscussion);
    }

    @Transactional
    public void deleteById(int id){
        dateDiscussionDAO.deleteById(id);
    }

    @Transactional
    public Iterable<DateDiscussion> getAll(){
        return dateDiscussionDAO.findAll();
    }

    @Transactional
    public DateDiscussion findById(int id){
        return dateDiscussionDAO.findById(id).get();
    }

    @Transactional
    public List<DateDiscussion> getDateDiscussionsByDatingId(int datingId){
        return dateDiscussionDAO.getDateDiscussionsByDatingId(datingId);
    }
}
