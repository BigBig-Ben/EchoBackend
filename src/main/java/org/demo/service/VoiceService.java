package org.demo.service;

import java.util.List;
import java.util.Optional;

import org.demo.DAO.VoiceDAO;
import org.demo.Entity.Voice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class VoiceService {
	@Autowired
	private VoiceDAO voiceDAO;
	
	public void save(Voice voice)
	{
		voiceDAO.save(voice);
	}
	
	public void delete(int id)
	{
		voiceDAO.deleteById(id);
	}
	
	public void update(Voice voice)
	{
		voiceDAO.save(voice);
	}
	
	public List<Voice> getAll()
	{
		return (List<Voice>) voiceDAO.findAll();
	}
	
	public Voice getVoiceById(int id)
	{
		return voiceDAO.findById(id).get();
	}
	
	public List<Voice> findOrderByStarsDesc()
	{
		return voiceDAO.findOrderByStarsDesc();
	}
	
	public List<Voice> findOrderByTimeDesc()
	{
		return voiceDAO.findOrderByTimeDesc();
	}
	
}
