package com.spms.sports.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.spms.sports.entity.Gender;
import com.spms.sports.entity.Player;
import com.spms.sports.entity.Sport;
import com.spms.sports.repository.PlayerRepository;
import com.spms.sports.repository.SportRepository;

@SpringBootTest
public class PlayerServiceTest 
{	
	@InjectMocks
	PlayerService playerService;
	
	@Mock
	PlayerRepository playerRepository;
	
	@Mock
	private SportRepository sportRepository;
	
	private Player player;
    private Set<Sport> newSports;

    @BeforeEach
    void setUp() 
    {
        // Setup before each test
        player = new Player("player1@example.com", 5, 25, Gender.male, new HashSet<>());
        Sport soccer = new Sport("Soccer");
        Sport tennis = new Sport("Tennis");
        newSports = new HashSet<>(Set.of(soccer, tennis));

        when(playerRepository.findById(player.getEmail())).thenReturn(Optional.of(player));
        when(sportRepository.findByName("Soccer")).thenReturn(Optional.of(soccer));
        when(sportRepository.findByName("Tennis")).thenReturn(Optional.of(tennis));
    }

    @Test
    void updatePlayerSportsTest() 
    {
        Set<String> sportNames = Set.of("Soccer", "Tennis");

        playerService.updatePlayerSports(player.getEmail(), sportNames);

        // Verify that the repository's save method was called with our player
        verify(playerRepository).save(player);
        // Verify that the player now has the correct sports
        assert player.getSports().equals(newSports) : "The player's sports were not updated correctly";

        // Optionally, verify that repository methods for finding sports and players were called
        verify(playerRepository, times(1)).findById(any(String.class));
        verify(sportRepository, times(sportNames.size())).findByName(any(String.class));
    }
	
	@Test
	public void getPlayersWithNoSportTest() 
	{
		// Setup
        Player player1 = new Player("player1@example.com", 5, 25, Gender.male, Set.of());
        Player player2 = new Player("player2@example.com", 4, 22, Gender.female, Set.of());

        List<Player> mockPlayers = Arrays.asList(player1, player2);
        when(playerRepository.findPlayersWithNoSport()).thenReturn(mockPlayers);

        // Execute the service call
        List<Player> returnedPlayers = playerService.getPlayersWithNoSport();

        // Assert the response
        assertEquals(2, returnedPlayers.size(), "Expected to find 2 players with no sports");
        assertEquals(mockPlayers, returnedPlayers, "The list of players returned was not what was expected");
	}			
	
	@Test
    void getPlayersFilteredBySportsTest() 
	{
        // Setup
        List<String> sportNames = List.of("Soccer", "Tennis");
        Page<Player> expectedPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        when(playerRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(expectedPage);

        // Action
        Page<Player> resultPage = playerService.getPlayersFilteredBySports(sportNames, 0);

        // Assertion
        assertThat(resultPage).isEqualTo(expectedPage);
        verify(playerRepository).findAll(any(Specification.class), any(PageRequest.class));
    }
}
