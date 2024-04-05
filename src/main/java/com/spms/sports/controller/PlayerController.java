package com.spms.sports.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spms.sports.entity.Player;
import com.spms.sports.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = {"http://localhost:4200","http://192.168.86.250:4200"})
public class PlayerController 
{
	private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);	 
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //B-2
    //curl --location 'http://localhost:8080/api/players/no-sport'
    @Operation(summary = "Get players without associated sports")
    @ApiResponse(responseCode = "200", description = "Found players",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    @ApiResponse(responseCode = "204", description = "No players found")    
    @GetMapping("/no-sport")
    public ResponseEntity<List<Player>> getPlayersWithNoSport() 
    {
    	logger.info("Request received: getPlayersWithNoSport");
        List<Player> players = playerService.getPlayersWithNoSport();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(players);
    }
    
    //B-3
    //curl --location --request PUT 'http://192.168.86.250:8080/api/players/player9@example.com/sports' \--header 'Content-Type: application/json' \--data '["Boxing", "Tennis"]'
    @Operation(summary = "Update a player's sports")
    @ApiResponse(responseCode = "200", description = "Player's sports updated")
    @PutMapping("/{email}/sports")
    public ResponseEntity<?> updatePlayerSports(@Parameter(description = "Email of the player to update") @PathVariable String email, @RequestBody Set<String> sportNames) 
    {
    	logger.info("Request received: updatePlayerSports for email {}", email);
        playerService.updatePlayerSports(email, sportNames);
        return ResponseEntity.ok().build();
    }
    
    //B-5
    //curl --location 'http://localhost:8080/api/players?sports=Soccer&sports=Basketball&page=0'
    @Operation(summary = "Get players filtered by sports")
    @ApiResponse(responseCode = "200", description = "Players filtered by sports",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    @GetMapping
    public Page<Player> getPlayers(@RequestParam(required = false) List<String> sports, @RequestParam(defaultValue = "0") int page) 
    {
    	logger.info("Request received: getPlayers for sports {}", sports);
        return playerService.getPlayersFilteredBySports(sports, page);
    }
}
