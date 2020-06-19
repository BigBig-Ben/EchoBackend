package org.demo.DAO;

import java.util.List;

import org.demo.Entity.Voice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VoiceDAO extends CrudRepository<Voice, Integer> {
    @Query(value = "select v from Voice v order by v.stars desc")
    public List<Voice> getVoicesOrderByStarsDesc();

    @Query(value = "select v from Voice v order by v.time desc")
    public List<Voice> getVoicesOrderByTimeDesc();

    @Query(value = "select * from voice where host_id = :userId order by time", nativeQuery = true)
    public List<Voice> getVoicesByUserId(@Param("userId") int userId);

}
