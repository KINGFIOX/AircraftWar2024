package com.example.aircraftwar2024.web.structs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameBegin {
    private String begin;

    @JsonCreator
    public GameBegin(@JsonProperty("begin") String begin) {
        this.begin = begin;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }
}
