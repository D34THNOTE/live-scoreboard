import main.models.Match;
import main.models.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {

    Scoreboard scoreboard;

    @BeforeEach
    void setup() {
        scoreboard = new Scoreboard();
    }

    @Test
    void newScoreboard_shouldStartEmpty() {
        assertTrue(scoreboard.getMatches().isEmpty());
    }

    @Test
    void startMatch_shouldAddMatchToScoreboard() {
        Match match = scoreboard.startMatch("Germany", "France");

        List<Match> matches = scoreboard.getMatches();

        assertEquals(1, matches.size());
        assertTrue(matches.contains(match));
    }

    @Test
    void startMatch_shouldInitializeZeroScore() {
        Match match = scoreboard.startMatch("Germany", "France");

        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void startMatch_shouldPropagateException_whenInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(null, "Away"));
    }

    @Test
    void updateScore_shouldUpdateMatchScoresCorrectly() {
        Match match = scoreboard.startMatch("Germany", "France");

        scoreboard.updateScore(match, 5, 8);

        assertEquals(5, match.getHomeScore());
        assertEquals(8, match.getAwayScore());
    }

    @Test
    void updateScore_shouldOverwritePreviousScore() {
        Match match = scoreboard.startMatch("Germany", "France");

        scoreboard.updateScore(match, 5, 8);
        scoreboard.updateScore(match, 1, 1);

        assertEquals(1, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
    }
}
