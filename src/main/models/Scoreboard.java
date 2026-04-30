package main.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {

    private final List<Match> matches = new ArrayList<>();

    private int orderCounter = 0;

    public Scoreboard() {

    }

    public Match startMatch(String homeName, String awayName) {
        Match match = new Match(homeName, awayName, orderCounter);
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
        if(match == null || !matches.contains(match)) {
            throw new IllegalArgumentException("Provided match does not exist");
        }

        matches.remove(match);
    }

    public List<Match> getSortedMatches() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore).reversed()
                        .thenComparing(
                                Comparator.comparingInt(Match::getOrderId).reversed()
                        )
                ).toList();
    }

    public String getSummary() {
        List<Match> sortedMatches = getSortedMatches();

        StringBuilder result = new StringBuilder();

        for(int i = 0; i < sortedMatches.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(sortedMatches.get(i).toString());

            if(i < sortedMatches.size() - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }
}
