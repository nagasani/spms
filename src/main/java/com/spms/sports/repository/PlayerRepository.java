package com.spms.sports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.spms.sports.entity.Gender;
import com.spms.sports.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String>, JpaSpecificationExecutor<Player> 
{
	//@Query("SELECT p FROM Player p WHERE p.gender = :gender AND p.level = :level AND p.age = :age")
    List<Player> findByGenderAndLevelAndAge(Gender gender, int level, int age);
    
    @Query("SELECT p FROM Player p WHERE p.sports IS EMPTY")
    List<Player> findPlayersWithNoSport();
}
