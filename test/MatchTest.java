import main.models.Match;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    @Test
    void constructor_shouldThrow_whenNamesAreNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Match(null, "Away", 0));
        assertThrows(IllegalArgumentException.class, () -> new Match("Home", null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Match("", "Away", 0));
        assertThrows(IllegalArgumentException.class, () -> new Match("Home", "", 0));
    }

    @Test
    void constructor_shouldThrow_whenTeamsAreTheSame() {
        assertThrows(IllegalArgumentException.class, () -> new Match("Same", "Same", 0));
        assertThrows(IllegalArgumentException.class, () -> new Match("same", "Same", 0));
        assertThrows(IllegalArgumentException.class, () -> new Match("SAME", "same", 0));
    }

    @Test
    void constructor_shouldInitializeScoreToZero() {
        Match match = new Match("Germany", "Spain", 0);

        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void updateScore_shouldThrow_whenNegativeValues() {
        Match match = new Match("Germany", "Spain", 0);

        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(0, -1));
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-1, -1));
    }

    @Test
    void updateScore_shouldUpdateScoresCorrectly() {
        Match match = new Match("Germany", "Spain", 0);

        match.updateScore(2, 3);

        assertEquals(2, match.getHomeScore());
        assertEquals(3, match.getAwayScore());
    }

    @Test
    void getTotalScore_shouldReturnCorrectSum() {
        Match match = new Match("Germany", "Spain", 0);

        match.updateScore(4, 5);

        assertEquals(9, match.getTotalScore());
    }
}
