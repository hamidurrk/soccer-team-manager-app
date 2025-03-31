package com.main.soccer.iterator;

import com.main.soccer.model.Player;
import java.util.List;
import java.util.NoSuchElementException;

public class PlayerIterator implements CustomIterator<Player> {
    private final List<Player> players;
    private int position;

    public PlayerIterator(List<Player> players) {
        this.players = players;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < players.size();
    }

    @Override
    public Player next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return players.get(position++);
    }
} 