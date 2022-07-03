package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class Player extends AbstractEntity {

    private String name;
    private Integer score = 3;
    private String status;
    private Double randomscore = 0.0;
    private Double enteredscore = 0.0;


    public Player(String name){
        this.name= name;
        this.status = "alive";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setScore(Integer score) {
    	this.score = score;
    }

    public Integer getScore() {
        return score;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }
    
    public String getStatus() {
    	return status;
    }

    public void setRandomscore(Double randomscore) {
    	this.randomscore = randomscore;
    }
    
    public Double getRandomscore() {
    	return randomscore;
    }

    public void setEnteredscore(Double enteredscore) {
    	this.enteredscore = enteredscore;
    }
    
    public Double getEnteredscore() {
    	return enteredscore;
    }
}
