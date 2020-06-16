package org.demo.service;

import org.demo.DAO.TagDAO;
import org.demo.Entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagDAO tagDAO;

    @Transactional
    public void save(Tag label) {
        tagDAO.save(label);
    }

    @Transactional
    public List<Tag> getAll() {
        return (List<Tag>) tagDAO.findAll();
    }

    @Transactional
    public Tag findById(int id) {
        return tagDAO.findById(id).get();
    }
}
