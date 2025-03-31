package com.main.soccer.model;

import java.util.UUID;

public class Match implements SoccerEntity {
    private final String id;
    private final String homeTeam;
    private final String awayTeam;
    private final String score;
    private final String competition;
    private final String date;
    private final String venue;

    public Match(String homeTeam, String awayTeam, String score, String competition, String date, String venue) {
        this.id = UUID.randomUUID().toString();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
        this.competition = competition;
        this.date = date;
        this.venue = venue;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return homeTeam + " vs " + awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getScore() {
        return score;
    }

    public String getCompetition() {
        return competition;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }
} 