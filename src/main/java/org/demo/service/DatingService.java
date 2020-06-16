package org.demo.service;

import org.demo.DAO.DatingDAO;
import org.demo.Entity.Dating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatingService {
    @Autowired
    private DatingDAO datingDAO;

    public void save(Dating dating)
    {
        datingDAO.save(dating);
    }

    public void delete(int id)
    {
        datingDAO.deleteById(id);
    }

    public void update(Dating dating)
    {
        datingDAO.save(dating);
    }

    public List<Dating> getAll()
    {
        return (List<Dating>) datingDAO.findAll();
    }

    public Dating getDatingById(int id)
    {
        return datingDAO.findById(id).get();
    }

    //pass
}
