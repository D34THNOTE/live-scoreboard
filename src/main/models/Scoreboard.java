package main.models;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private final List<Match> matches = new ArrayList<>();

    public Scoreboard() {

    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches);
    }
}
