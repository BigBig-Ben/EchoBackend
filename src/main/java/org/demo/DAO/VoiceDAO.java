package org.demo.DAO;

import java.util.List;
import org.demo.Entity.Voice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoiceDAO extends CrudRepository<Voice,Integer>{

	@Query("select v from Voice v order by v.stars desc")
	public List<Voice> findOrderByStarsDesc();

	@Query("select v from Voice v order by v.time desc")
	public List<Voice> findOrderByTimeDesc();

}
