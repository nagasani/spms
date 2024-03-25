package com.spms.sports.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.spms.sports.entity.Player;
import com.spms.sports.entity.Sport;
import com.spms.sports.repository.PlayerRepository;
import com.spms.sports.repository.SportRepository;

@SpringBootTest
public class SportsServiceTest 
{	
	@InjectMocks
	SportService sportService;
	
	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private SportRepository sportRepository;	

	@Test
    void deleteSportAndAssociationsTest() 
	{
		String sportName = "Soccer";
        Sport sport = new Sport(); 
        sport.setName(sportName);
        
        Player player1 = new Player(); 
        Player player2 = new Player();
        Set<Player> players = new HashSet<>(Set.of(player1, player2));
        sport.setPlayers(players); 

        when(sportRepository.findByName(sportName)).thenReturn(Optional.of(sport));

        // Action
        sportService.deleteSportAndAssociations(sportName);

        // Assertions
        verify(sportRepository).findByName(sportName);
        verify(playerRepository).saveAll(players); 
        verify(sportRepository).delete(sport); 		
	}
	
	@Test
	public void findSportsByNamesTest() 
	{
		// Given
        List<String> names = Arrays.asList("Football", "Basketball");
        List<Sport> expectedSports = Arrays.asList(new Sport("Football"), new Sport("Basketball"));
        when(sportRepository.findByNameIn(names)).thenReturn(expectedSports);

        // When
        List<Sport> actualSports = sportService.findSportsByNames(names);

        // Then
        assertEquals(expectedSports.size(), actualSports.size(), "The size of the returned sports list should match the expected size.");
        assertEquals(expectedSports.get(0).getName(), actualSports.get(0).getName(), "The name of the first sport should match the expected name.");
        assertEquals(expectedSports.get(1).getName(), actualSports.get(1).getName(), "The name of the second sport should match the expected name.");
	}				
}
