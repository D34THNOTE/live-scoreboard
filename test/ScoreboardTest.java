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

    @Test
    void updateScore_shouldAllowZeroScores() {
        Match match = scoreboard.startMatch("Germany", "France");

        scoreboard.updateScore(match, 0, 0);

        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void updateScore_shouldThrow_whenMatchIsNull() {
        Match match = scoreboard.startMatch("Germany", "France");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(null, 1, 5));
    }

    @Test
    void updateScore_shouldThrow_whenMatchNotInScoreboard() {
        Scoreboard differentScoreboard = new Scoreboard();
        Match match = differentScoreboard.startMatch("Germany", "France");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(match, 1, 5));
    }

    @Test
    void updateScore_shouldPropagateException_whenInvalidInput() {
        Match match = scoreboard.startMatch("Germany", "France");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(match, -1, 1));
    }

    @Test
    void finishMatch_shouldRemoveMatchCorrectly() {
        Match match = scoreboard.startMatch("Germany", "France");
        assertTrue(scoreboard.getMatches().contains(match));
        scoreboard.finishMatch(match);

        assertFalse(scoreboard.getMatches().contains(match));
    }

    @Test
    void finishMatch_shouldThrow_whenMatchIsNull() {
        Match match = scoreboard.startMatch("Germany", "France");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch(null));
    }

    @Test
    void finishMatch_shouldThrow_whenMatchNotInScoreboard() {
        Scoreboard differentScoreboard = new Scoreboard();
        Match match = differentScoreboard.startMatch("Germany", "France");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch(match));
    }

    @Test
    void updateScore_shouldThrow_whenMatchWasFinished() {
        Match match = scoreboard.startMatch("Germany", "France");
        scoreboard.finishMatch(match);

        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(match, 2, 3));
    }

    @Test
    void getSortedMatches_shouldSortByTotalScoreCorrectly() {
        Match match1 = scoreboard.startMatch("A", "B");
        Match match2 = scoreboard.startMatch("C", "D");
        scoreboard.updateScore(match1, 5, 1);
        scoreboard.updateScore(match2, 0, 4);

        List<Match> sorted = scoreboard.getSortedMatches();

        assertTrue(match1.getTotalScore() > match2.getTotalScore());
        assertTrue(sorted.indexOf(match1) < sorted.indexOf(match2));
    }

    @Test
    void getSortedMatches_shouldReturnEmptyList() {
        List<Match> sorted = scoreboard.getSortedMatches();
        assertTrue(sorted.isEmpty());
    }

    @Test
    void getSortedMatches_shouldSortByMostRecent_whenScoresAreEqual() {
        Match match1 = scoreboard.startMatch("A", "B");
        Match match2 = scoreboard.startMatch("C", "D");

        scoreboard.updateScore(match1, 2, 2);
        scoreboard.updateScore(match2, 2, 2);

        List<Match> sorted = scoreboard.getSortedMatches();

        assertTrue(sorted.indexOf(match2) < sorted.indexOf(match1));
    }

    @Test
    void getSortedMatches_shouldSortByTotalScoreCorrectly_forManyMatches() {
        Match match1 = scoreboard.startMatch("A", "B");
        Match match2 = scoreboard.startMatch("C", "D");
        Match match3 = scoreboard.startMatch("E", "F");
        Match match4 = scoreboard.startMatch("G", "H");
        Match match5 = scoreboard.startMatch("I", "J");
        scoreboard.updateScore(match1, 5, 1); // 6
        scoreboard.updateScore(match2, 0, 4); // 4
        scoreboard.updateScore(match3, 0, 0); // 0
        scoreboard.updateScore(match4, 10, 4); // 14
        scoreboard.updateScore(match5, 0, 3); // 3

        List<Match> sorted = scoreboard.getSortedMatches();

        assertTrue(sorted.indexOf(match4) < sorted.indexOf(match1));
        assertTrue(sorted.indexOf(match1) < sorted.indexOf(match2));
        assertTrue(sorted.indexOf(match2) < sorted.indexOf(match5));
        assertTrue(sorted.indexOf(match5) < sorted.indexOf(match3));
    }

    @Test
    void getSortedMatches_shouldReturnSingleElement() {
        Match match1 = scoreboard.startMatch("A", "B");
        List<Match> sorted = scoreboard.getSortedMatches();
        assertEquals(1, sorted.size());
        assertEquals(match1, sorted.get(0));
    }

    @Test
    void getSortedMatches_shouldApplyScoreAndRecencyRulesTogether() {
        Match match1 = scoreboard.startMatch("A", "B");
        Match match2 = scoreboard.startMatch("C", "D");
        Match match3 = scoreboard.startMatch("E", "F");

        // Same score
        scoreboard.updateScore(match1, 3, 2);
        scoreboard.updateScore(match2, 4, 1);

        // Lower score
        scoreboard.updateScore(match3, 1, 1);

        List<Match> sorted = scoreboard.getSortedMatches();

        // Recency rule
        assertTrue(sorted.indexOf(match2) < sorted.indexOf(match1));

        // Score rule
        assertTrue(sorted.indexOf(match1) < sorted.indexOf(match3));
        assertTrue(sorted.indexOf(match2) < sorted.indexOf(match3));
    }

    @Test
    void getSummary_shouldReturnCorrectStringForSingleMatch() {
        Match match = scoreboard.startMatch("Albania", "Bulgaria");
        scoreboard.updateScore(match, 3, 2);

        String expected = "1. Albania 3 - 2 Bulgaria";
        assertEquals(expected, scoreboard.getSummary());
    }

    @Test
    void getSummary_shouldReturnEmptyString() {
        assertEquals("", scoreboard.getSummary());
    }
}
