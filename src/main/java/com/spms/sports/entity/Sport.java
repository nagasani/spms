package com.spms.sports.entity;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sports")
@Schema(description = "Represents a sport that players can be associated with.")
public class Sport 
{
	@Id
	@Column(nullable = false)
	@Schema(description = "Name of the sport, serves as the unique identifier.", example = "Soccer", required = true)    
	private String name;

	@ManyToMany(mappedBy = "sports")
	@JsonManagedReference
	@Schema(description = "Set of players associated with this sport.")    
	private Set<Player> players = new HashSet<>();

	// Constructor
	public Sport() {
	}

	public Sport(String name) {
		this.name = name;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	// Utility methods
	public void addPlayer(Player player) {
		this.players.add(player);
		player.getSports().add(this);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
		player.getSports().remove(this);
	}
}