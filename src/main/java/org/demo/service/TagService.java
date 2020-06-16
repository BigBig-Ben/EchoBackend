package org.demo.service;

import org.demo.DAO.TagDAO;
import org.demo.Entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
	@Autowired
	private TagDAO labelDAO;
	
	@Transactional
	public void save(Tag label)
	{
		labelDAO.save(label);
	}
}
