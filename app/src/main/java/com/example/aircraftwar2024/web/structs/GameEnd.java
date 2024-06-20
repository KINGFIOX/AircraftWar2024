package com.example.aircraftwar2024.web.structs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameEnd {
    private final String message = "end";
    private int score;

    @JsonCreator
    public GameEnd(@JsonProperty("score") int score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
