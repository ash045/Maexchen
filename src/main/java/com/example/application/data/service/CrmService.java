package com.example.application.data.service;

import com.example.application.data.entity.Player;
import com.example.application.data.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
	
	private final PlayerRepository playerRepository;


    public CrmService(PlayerRepository playerRepository) { 
        this.playerRepository = playerRepository;
    }
 
    public List<Player> findAllPlayers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return playerRepository.findAll();
        } else {
            return playerRepository.search(stringFilter);
        }
    }
    
    public long countplayer() {
        return playerRepository.count();
    }
}
