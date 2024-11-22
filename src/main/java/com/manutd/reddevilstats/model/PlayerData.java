package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerData {
    private final Player player;
    private final List<Statistics> statistics;

    @JsonCreator
    public PlayerData(@JsonProperty("player") Player player, @JsonProperty("statistics") List<Statistics> statistics){
        this.player = player;
        this.statistics = statistics;
    }

    public Player getPlayer(){
        return player;
    }

    public List<Statistics> getStatistics(){
        return statistics;
    }
}
