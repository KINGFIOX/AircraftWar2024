package com.example.aircraftwar2024.web.structs;


import com.fasterxml.jackson.annotation.JsonInclude;

// Score class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Score {

    private int message;

    public Score(int score) {
        this.message = score;
    }

    public int getMessage() {
        return message;
    }

}
