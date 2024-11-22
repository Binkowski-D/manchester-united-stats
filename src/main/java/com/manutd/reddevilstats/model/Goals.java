package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Goals {
    private final int total;
    private final int assists;
    private final int saves;

    @JsonCreator
    public Goals(@JsonProperty("total") int total,
                 @JsonProperty("assists") int assists,
                 @JsonProperty("saves") int saves){
        this.total = total;
        this.assists = assists;
        this.saves = saves;
    }

    public int getTotal(){
        return total;
    }

    public int getAssists(){
        return assists;
    }

    public int getSaves(){
        return saves;
    }
}
