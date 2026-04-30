## Live Football World Cup Scoreboard

### Overview

This project implements a simple in-memory library for managing a live football scoreboard.

It supports:
- starting matches
- updating scores
- finishing matches
- retrieving a summary ordered by total score and recency

### Assumptions / Design decisions
- The `Scoreboard` class is the main entry point and exposes all operations
- `updateScore` operates on a `Match` reference rather than identifiers for simplicity
- Match recency is tracked using an internal counter
- Scores can decrease
- Team names are not required to be globally unique
- No validation is enforced on team name format (no constraints specified)
