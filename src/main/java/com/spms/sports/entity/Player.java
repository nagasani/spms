package com.spms.sports.entity;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player 
{
	@Id
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private int level;

	@Column(nullable = false)
	private int age;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "player_sport", joinColumns = @JoinColumn(name = "player_email"), inverseJoinColumns = @JoinColumn(name = "sport_name"))
	@JsonBackReference
	private Set<Sport> sports = new HashSet<>();

	// Constructor
	public Player() {
	}

	public Player(String email, int level, int age, Gender gender) {
		this.email = email;
		this.level = level;
		this.age = age;
		this.gender = gender;
	}
	
	public Player(String email, int level, int age, Gender gender, Set<Sport> sports) {
		this.email = email;
		this.level = level;
		this.age = age;
		this.gender = gender;
		this.sports = sports;
	}

	// Getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Set<Sport> getSports() {
		return sports;
	}

	public void setSports(Set<Sport> sports) {
		this.sports = sports;
	}

	// Utility methods
	public void addSport(Sport sport) {
		this.sports.add(sport);
		sport.getPlayers().add(this);
	}

	public void removeSport(Sport sport) {
		this.sports.remove(sport);
		sport.getPlayers().remove(this);
	}
}

////@Query("SELECT p FROM Player p WHERE p.sport IS NULL")
