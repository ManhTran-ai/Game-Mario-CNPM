package manager;

/**
 * Component ID: CLS-03
 * Purpose: Enumerates all discrete button/action inputs recognized by the game input layer.
 * Owner: Member 1
 * Ref UML: CD, AD01
 * Derivation: Mapped from keyboard/mouse events in InputManager to semantic game actions.
 */
public enum ButtonAction {
    JUMP,
    M_RIGHT,
    M_LEFT,
    CROUCH,
    FIRE,
    START,
    PAUSE_RESUME,
    ACTION_COMPLETED,
    SELECT,
    GO_UP,
    GO_DOWN,
    GO_TO_START_SCREEN,
    NO_ACTION
}