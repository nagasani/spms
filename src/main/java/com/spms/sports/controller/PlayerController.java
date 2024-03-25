package com.spms.sports.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spms.sports.entity.Player;
import com.spms.sports.service.PlayerService;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //B-2
    //curl --location 'http://localhost:8080/api/players/no-sport'
    @GetMapping("/no-sport")
    public ResponseEntity<List<Player>> getPlayersWithNoSport() {
        List<Player> players = playerService.getPlayersWithNoSport();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(players);
    }
    
    //B-3
    //curl --location --request PUT 'http://192.168.86.250:8080/api/players/player9@example.com/sports' \--header 'Content-Type: application/json' \--data '["Boxing", "Tennis"]'
    @PutMapping("/{email}/sports")
    public ResponseEntity<?> updatePlayerSports(@PathVariable String email, @RequestBody Set<String> sportNames) 
    {
        playerService.updatePlayerSports(email, sportNames);
        return ResponseEntity.ok().build();
    }
    
    //B-5
    //curl --location 'http://localhost:8080/api/players?sports=Soccer&sports=Basketball&page=0'
    @GetMapping
    public Page<Player> getPlayers(@RequestParam(required = false) List<String> sports, @RequestParam(defaultValue = "0") int page) {
        return playerService.getPlayersFilteredBySports(sports, page);
    }
}
