package com.example.aircraftwar2024.web;


import com.fasterxml.jackson.annotation.JsonInclude;

// GameEnd class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameEnd {

    GameEnd(int score) {
        this.score = score;
    }

    final private String message = "end";
    private int score;

    // getters and setters
    public String getMessage() {
        return message;
    }

    public int getScore() {
        return score;
    }

}
