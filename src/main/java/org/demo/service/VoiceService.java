package org.demo.service;

import java.util.List;

import org.demo.DAO.VoiceDAO;
import org.demo.Entity.Voice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoiceService {
	@Autowired
	private VoiceDAO voiceDAO;

	@Transactional
	public void save(Voice voice)
	{
		voiceDAO.save(voice);
	}

	@Transactional
	public void delete(int id)
	{
		voiceDAO.deleteById(id);
	}

	@Transactional
	public void update(Voice voice)
	{
		voiceDAO.save(voice);
	}

	@Transactional
	public List<Voice> getVoicesByUserId(int userId)
	{
		return (List<Voice>) voiceDAO.getVoicesByUserId(userId);
	}

	@Transactional
	public Voice getVoiceById(int id)
	{
		return voiceDAO.findById(id).get();
	}

	@Transactional
	public List<Voice> getVoicesOrderByStarsDesc()
	{
		return voiceDAO.getVoicesOrderByStarsDesc();
	}

	@Transactional
	public List<Voice> getVoicesOrderByTimeDesc()
	{
		return voiceDAO.getVoicesOrderByTimeDesc();
	}
	
}
