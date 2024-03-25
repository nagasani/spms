package com.spms.sports.entity;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sports")
public class Sport 
{
	@Id
	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "sports")
	@JsonManagedReference
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