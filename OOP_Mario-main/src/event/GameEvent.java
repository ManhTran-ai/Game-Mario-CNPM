package event;

/**
 * Component ID: CLS-05
 * Purpose: Enumerates all game events that can be published via EventBus for decoupled sound/reaction triggers.
 * Owner: Member 1
 * Ref UML: CD, AD02
 * Derivation: Centralized event types consumed by GameController event handlers for sound playback.
 */
public enum GameEvent {
    JUMP,
    COIN_COLLECTED,
    FIREBALL_FIRED,
    STOMP_ENEMY,
    ONE_UP,
    SUPER_MUSHROOM,
    FIRE_FLOWER,
    MARIO_DIES,
    CAMERA_SHAKE
}