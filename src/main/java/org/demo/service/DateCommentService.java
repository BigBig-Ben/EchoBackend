package org.demo.service;

import org.demo.DAO.DateCommentDAO;
import org.demo.Entity.DateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DateCommentService {
    @Autowired
    private DateCommentDAO dateCommentDAO;

    @Transactional
    public void save(DateComment dataComment) {
        dateCommentDAO.save(dataComment);
    }

    @Transactional
    public void update(DateComment dateComment) {
        dateCommentDAO.save(dateComment);
    }

    @Transactional
    public void deleteById(int id){
        dateCommentDAO.deleteById(id);
    }

    @Transactional
    public Iterable<DateComment> getAll(){
        return dateCommentDAO.findAll();
    }

    @Transactional
    public DateComment findById(int id){
        return dateCommentDAO.findById(id).get();
    }

    @Transactional
    public int count(){
        return (int) dateCommentDAO.count();
    }

}
