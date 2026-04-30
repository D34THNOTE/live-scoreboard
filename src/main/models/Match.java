package main.models;

public class Match {
    private final String homeName;
    private final String awayName;

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
    }
}
