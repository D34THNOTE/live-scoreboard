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
}
