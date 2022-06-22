package com.example.application.data.repository;

import com.example.application.data.entity.Player;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
	
	@Query("select p from Player p " +
		      "where lower(p.name) like lower(concat('%', :searchTerm, '%')) ") 
		    List<Player> search(@Param("searchTerm") String searchTerm); 
}
