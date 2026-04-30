package main.models;

public class Match {
    private final String homeName;
    private final String awayName;

    private int homeScore;
    private int awayScore;

    public Match(String homeName, String awayName, int orderId) {
        if(homeName == null || awayName == null ||
                homeName.isEmpty() || awayName.isEmpty()) {
            throw new IllegalArgumentException("Team names must not be null or empty");
        }

        if(homeName.equalsIgnoreCase(awayName)) {
            throw new IllegalArgumentException("Home and away teams must be different");
        }

        this.homeName = homeName;
        this.awayName = awayName;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public void updateScore(int homeScore, int awayScore) {
        if(homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Match score cannot be negative");
        }

        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
