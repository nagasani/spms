package com.spms.sports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spms.sports.entity.Sport;
import com.spms.sports.exception.SportNotFoundException;
import com.spms.sports.repository.PlayerRepository;
import com.spms.sports.repository.SportRepository;

import jakarta.transaction.Transactional;

@Service
public class SportService {

	private final SportRepository sportRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public SportService(SportRepository sportRepository, PlayerRepository playerRepository) {
        this.sportRepository = sportRepository;
        this.playerRepository = playerRepository;
    }
    
    public List<Sport> getSportsWithMultiplePlayers() 
    {
        return sportRepository.findSportsWithMultiplePlayers();
    }
    
    public List<Sport> getSportsWithNoPlayers() 
    {
        return sportRepository.findSportsWithNoPlayers();
    }

    // jUnit
    public List<Sport> findSportsByNames(List<String> names) {
        return sportRepository.findByNameIn(names);
    }
    
    //JUnit
    @Transactional
    public void deleteSportAndAssociations(String sportName) {
        Sport sport = sportRepository.findByName(sportName)
                .orElseThrow(() -> new SportNotFoundException("Sport not found: " + sportName));

        sport.getPlayers().forEach(player -> player.getSports().remove(sport));
        playerRepository.saveAll(sport.getPlayers());
        
        sportRepository.delete(sport);
    }
}
