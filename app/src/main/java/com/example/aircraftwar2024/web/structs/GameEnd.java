package com.example.aircraftwar2024.web.structs;


import com.fasterxml.jackson.annotation.JsonInclude;

// GameEnd class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameEnd {

    final private String message = "end";
    private int score;

    public GameEnd(int score) {
        this.score = score;
    }

    // getters and setters
    public String getMessage() {
        return message;
    }

    public int getScore() {
        return score;
    }

}
