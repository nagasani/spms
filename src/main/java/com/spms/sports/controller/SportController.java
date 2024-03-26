package com.spms.sports.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spms.sports.entity.Sport;
import com.spms.sports.service.SportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/sports")
public class SportController 
{
	private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    //B-1
    //curl --location 'http://localhost:8080/api/sports?names=Soccer&names=Tennis'
    @Operation(summary = "Get sports by names",
            description = "Returns a list of sports filtered by the given names")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
              content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Sport.class))})
    @GetMapping
    public ResponseEntity<?> getSportsByNames(@RequestParam List<String> names) 
    {
    	logger.info("Request received: getSportsByNames "+names);
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
    @Operation(summary = "Delete a sport by name",
            description = "Deletes a sport and its associations with players")
    @ApiResponse(responseCode = "200", description = "Sport deleted successfully")
    @DeleteMapping("/{sportName}")
    public ResponseEntity<?> deleteSport(@PathVariable String sportName) {
    	logger.info("Request received: deleteSport "+sportName);
        sportService.deleteSportAndAssociations(sportName);
        //return ResponseEntity.noContent().build()
        return ResponseEntity.ok().build();
    }
}