package com.example.aircraftwar2024.web;


import com.fasterxml.jackson.annotation.JsonInclude;

// Score class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Score {

    Score(int score) {
        this.message = score;
    }
    private int message;

    public int getMessage() {
        return message;
    }

}
