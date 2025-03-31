package com.main.soccer.iterator;

import com.main.soccer.model.Team;
import java.util.List;
import java.util.NoSuchElementException;

public class TeamIterator implements CustomIterator<Team> {
    private final List<Team> teams;
    private int position;

    public TeamIterator(List<Team> teams) {
        this.teams = teams;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < teams.size();
    }

    @Override
    public Team next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return teams.get(position++);
    }
} 