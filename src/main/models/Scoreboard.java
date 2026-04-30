package main.models;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private final List<Match> matches = new ArrayList<>();

    private int orderCounter = 0;

    public Scoreboard() {

    }

    public Match startMatch(String homeName, String awayName) {
        Match match = new Match(homeName, awayName, 0);
        matches.add(match);
        orderCounter++;
        return match;
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches);
    }

    public void updateScore(Match match, int homeScore, int awayScore) {
        if(match == null || !matches.contains(match)) {
            throw new IllegalArgumentException("Provided match does not exist");
        }

        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(Match match) {

    }
}
