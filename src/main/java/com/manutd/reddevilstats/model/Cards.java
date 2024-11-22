package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cards {
    private final int yellow;
    private final int red;

    @JsonCreator
    public Cards(@JsonProperty("yellow") int yellow,
                 @JsonProperty("red") int red){
        this.yellow = yellow;
        this.red = red;
    }

    public int getYellow(){
        return yellow;
    }

    public int getRed(){
        return red;
    }
}
