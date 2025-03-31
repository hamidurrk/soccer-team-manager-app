package com.main.soccer.model;

import java.util.UUID;

public class Player implements SoccerEntity {
    private final String id;
    private final String name;
    private final int age;
    private final String nationality;
    private final String position;
    private final String team;
    private final int number;

    public Player(String name, int age, String nationality, String position, String team, int number) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.position = position;
        this.team = team;
        this.number = number;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    public int getNumber() {
        return number;
    }
} 