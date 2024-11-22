package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Passes {
    private final int total;
    private final int key;
    private final int accuracy;

    @JsonCreator
    public Passes(@JsonProperty("total") int total,
                  @JsonProperty("key") int key,
                  @JsonProperty("accuracy") int accuracy){
        this.total = total;
        this.key = key;
        this.accuracy = accuracy;
    }

    public int getTotal(){
        return total;
    }

    public int getKey(){
        return key;
    }

    public int getAccuracy(){
        return accuracy;
    }
}
