package com.example.aircraftwar2024.web.structs;

import com.fasterxml.jackson.annotation.JsonInclude;

// GameBegin class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameBegin {
    private String message = "begin";

    // getters and setters
    public String getMessage() {
        return message;
    }

}

