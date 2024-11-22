package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {
    private final Games games;
    private final Goals goals;
    private final Passes passes;
    private final Cards cards;

    @JsonCreator
    public Statistics(@JsonProperty("games") Games games,
                      @JsonProperty("goals") Goals goals,
                      @JsonProperty("passes") Passes passes,
                      @JsonProperty("cards") Cards cards){
        this.games = games;
        this.goals = goals;
        this.passes = passes;
        this.cards = cards;
    }

    public Games getGames() {
        return games;
    }

    public Goals getGoals() {
        return goals;
    }

    public Passes getPasses() {
        return passes;
    }

    public Cards getCards() {
        return cards;
    }
}

