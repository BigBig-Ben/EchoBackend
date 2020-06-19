package org.demo.service;

import org.demo.DAO.DatingDAO;
import org.demo.Entity.Dating;
import org.demo.Entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatingService {
    @Autowired
    private DatingDAO datingDAO;

    @Transactional
    public void save(Dating dating)
    {
        datingDAO.save(dating);
    }

    @Transactional
    public void delete(int id)
    {
        datingDAO.deleteById(id);
    }

    @Transactional
    public void update(Dating dating)
    {
        datingDAO.save(dating);
    }

    @Transactional
    public List<Dating> getAllDatings()
    {
        return (List<Dating>) datingDAO.getAllDatings();
    }

    @Transactional
    public List<Dating> getDatingsByTag(int tagId){
        return datingDAO.getDatingsByTag(tagId);
    }

    @Transactional
    public List<Dating> getDatingsByUserId(int userId){
        return datingDAO.getDatingsByUserId(userId);
    }

    @Transactional
    public Dating getDatingById(int id)
    {
        return datingDAO.findById(id).get();
    }

    @Transactional
    public void leaveDatingByOne(int datingId, int userId){
        datingDAO.leaveDatingByOne(datingId, userId);
    }

    @Transactional
    public Boolean unknown(){
        return false;
    }


}
