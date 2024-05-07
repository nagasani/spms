package com.spms.sports.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.spms.sports.entity.Gender;
import com.spms.sports.entity.Player;
import com.spms.sports.entity.Sport;
import com.spms.sports.exception.PlayerNotFoundException;
import com.spms.sports.exception.SportNotFoundException;
import com.spms.sports.repository.PlayerRepository;
import com.spms.sports.repository.SportRepository;
import com.spms.sports.repository.specification.PlayerSpecification;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;

@Service
public class PlayerService {

	private final PlayerRepository playerRepository;
    private final SportRepository sportRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, SportRepository sportRepository) {
        this.playerRepository = playerRepository;
        this.sportRepository = sportRepository;
    }

    public List<Player> getPlayersByCriteria(Gender gender, int level, int age) 
    {
        return playerRepository.findByGenderAndLevelAndAge(gender, level, age);
    }
    
    public List<Player> getPlayersWithNoSport() 
    {
        return playerRepository.findPlayersWithNoSport();
    }

    @Transactional
	public void updatePlayerSports(String email, Set<String> sportNames) 
	{
		Player player = playerRepository.findById(email)
				.orElseThrow(() -> new PlayerNotFoundException("Player not found with email: " + email));
        
        Set<Sport> sports = new HashSet<>();
        for (String name : sportNames) 
        {
            Sport sport = sportRepository.findByName(name)
            		.orElseThrow(() -> new SportNotFoundException("Sport not found with name: " + name));
            sports.add(sport);
        }
        //To Delete Existing and Add New
        player.setSports(sports);
        //To update existing
        //player.getSports().addAll(sports);
        playerRepository.save(player);	
	}
    
    @CircuitBreaker(name = "getPlayersCircuitBreaker", fallbackMethod = "getPlayersFallback")
    public Page<Player> getPlayersFilteredBySports(List<String> sportNames, int page) 
    {	
        return playerRepository.findAll(PlayerSpecification.hasSports(sportNames), PageRequest.of(page, 10));
    }    
    
    public Page<Player> getPlayersFallback(List<String> sportNames, int page, Throwable t) 
    {
        // Log the exception or handle accordingly
        System.err.println("Error during getPlayers: " + t.getMessage());

        // Define a Pageable with the same page number and size used in the primary method
        Pageable pageable = PageRequest.of(page, 10);

        // Returning an empty page with an appropriate HTTP status
        Page<Player> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        return emptyPage;
        //return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(emptyPage);
    }   
}
