package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Games {
    private final int appearences;
    private final int minutes;
    private final String position;
    private final String rating;

    @JsonCreator
    public Games(@JsonProperty("appearences") int appearences,
                 @JsonProperty("minutes") int minutes,
                 @JsonProperty("position") String position,
                 @JsonProperty("rating") String rating){
        this.appearences = appearences;
        this.minutes = minutes;
        this.position = position;
        this.rating = rating;
    }

    public int getAppearences(){
        return appearences;
    }

    public int getMinutes(){
        return minutes;
    }

    public String getPosition(){
        return position;
    }

    public String getRating(){
        return rating;
    }
}
