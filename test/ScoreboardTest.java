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
}
