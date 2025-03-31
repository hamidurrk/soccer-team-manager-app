package com.main.soccer.iterator;

import com.main.soccer.model.Match;
import java.util.List;
import java.util.NoSuchElementException;

public class MatchIterator implements CustomIterator<Match> {
    private final List<Match> matches;
    private int position;

    public MatchIterator(List<Match> matches) {
        this.matches = matches;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < matches.size();
    }

    @Override
    public Match next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return matches.get(position++);
    }
} 