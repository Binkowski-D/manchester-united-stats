package com.manutd.reddevilstats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
    private final String name;
    private final int age;
    private final String nationality;
    private final int number;
    private final String photo;


    @JsonCreator
    public Player(@JsonProperty("name") String name,
                  @JsonProperty("age") int age,
                  @JsonProperty("nationality") String nationality,
                  @JsonProperty("number") int number,
                  @JsonProperty("photo") String photo){

        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.number = number;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }

    public int getNumber() {
        return number;
    }

    public String getPhoto() {
        return photo;
    }
}
