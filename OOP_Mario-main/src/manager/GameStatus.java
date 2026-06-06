package manager;

/**
 * Component ID: CLS-02
 * Purpose: Represents all possible game states in the lifecycle of a Mario game session.
 * Owner: Member 1
 * Ref UML: CD, UC01
 * Derivation: Extracted from state machine design in AD01 (Game Loop diagram).
 */
public enum GameStatus {
    GAME_OVER,
    PAUSED,
    RUNNING,
    START_SCREEN,
    MAP_SELECTION,
    HELP_SCREEN,
    MISSION_PASSED,
    ABOUT_SCREEN
}