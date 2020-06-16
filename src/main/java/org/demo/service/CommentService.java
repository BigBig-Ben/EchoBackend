package org.demo.service;

import org.demo.DAO.CommentDAO;
import org.demo.Entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
	@Autowired
	CommentDAO commentDAO;
	
	@Transactional
	public void save(Comment comment)
	{
		commentDAO.save(comment);
	}
	
	@Transactional
	public void update(Comment comment)
	{
		commentDAO.save(comment);
	}
	
	@Transactional
	public void deleteById(int id)
	{
		commentDAO.deleteById(id);
	}
	
	@Transactional
	public Iterable<Comment> getAll()
	{
		return commentDAO.findAll();
	}

	@Transactional
	public Comment findById(int id) {
		return commentDAO.findById(id).get();
	}
	
	@Transactional
	public int count()
	{
		return (int) commentDAO.count();
	}
}
