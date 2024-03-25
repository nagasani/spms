package com.spms.sports.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spms.sports.entity.Sport;
import com.spms.sports.service.SportService;

@RestController
@RequestMapping("/api/sports")
public class SportController 
{
    private final SportService sportService;

    @Autowired
    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    //B-1
    //curl --location 'http://localhost:8080/api/sports?names=Soccer&names=Tennis'
    @GetMapping
    public ResponseEntity<?> getSportsByNames(@RequestParam List<String> names) 
    {
        try
        {
            List<Sport> sports = sportService.findSportsByNames(names);
            if (sports.isEmpty()) 
            {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(sports);
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("An error occurred while processing your request.");
        }
    }
    
    //B-4
    //curl --location --request DELETE 'http://localhost:8080/api/sports/soccer'
    @DeleteMapping("/{sportName}")
    public ResponseEntity<?> deleteSport(@PathVariable String sportName) {
        sportService.deleteSportAndAssociations(sportName);
        //return ResponseEntity.noContent().build()
        return ResponseEntity.ok().build();
    }
}