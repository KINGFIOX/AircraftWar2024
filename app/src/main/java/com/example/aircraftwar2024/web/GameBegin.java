package com.example.aircraftwar2024.web;

import com.fasterxml.jackson.annotation.JsonInclude;

// GameBegin class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameBegin {
    private String message;

    // getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
