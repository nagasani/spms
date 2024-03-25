package com.spms.sports.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.spms.sports.entity.Sport;

@Repository
public interface SportRepository extends JpaRepository<Sport, String> 
{
    @Query("SELECT s FROM Sport s JOIN s.players p GROUP BY s.name HAVING COUNT(p) >= 2")
    List<Sport> findSportsWithMultiplePlayers();
    
    @Query("SELECT s FROM Sport s WHERE s.players IS EMPTY")
    List<Sport> findSportsWithNoPlayers();    
    
    List<Sport> findByNameIn(List<String> names);
        
    Optional<Sport> findByName(String name);
}
