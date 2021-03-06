package org.demo.service;

import org.demo.DAO.CommentDAO;
import org.demo.Entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	public List<Comment> getCommentsByVoiceId(int voiceId){
		return commentDAO.getCommentsByVoiceId(voiceId);
	}

	@Transactional
	public List<Comment> getTheNewCommentByVoiceFloor(int voiceId, int floor){
		return commentDAO.getTheNewCommentByVoiceFloor(voiceId, floor);
	}
}
