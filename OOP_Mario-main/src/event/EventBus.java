package event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Component ID: CLS-04
 * Purpose: Singleton pub-sub bus enabling decoupled communication between game logic and reactive systems (sound, camera).
 * Owner: Member 1
 * Ref UML: CD, AD02
 * Derivation: Observer pattern centralized here; GameController subscribes handlers for each GameEvent type.
 */
public class EventBus {

    private static EventBus instance;
    private final Map<GameEvent, Consumer<Object>> listeners;

    private EventBus() {
        listeners = new HashMap<>();
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    /**
     * Method ID: MTH-001
     * Subscribes a handler to a specific game event.
     * @param event The event type to listen for.
     * @param handler The consumer to invoke when the event is posted.
     */
    public void subscribe(GameEvent event, Consumer<Object> handler) {
        listeners.put(event, handler);
    }

    /**
     * Posts an event with no associated data payload.
     * @param event The event type to post.
     */
    public void post(GameEvent event) {
        post(event, null);
    }

    /**
     * Method ID: MTH-002
     * Posts an event with an optional data payload.
     * @param event The event type to post.
     * @param data  Optional data passed to the handler.
     */
    public void post(GameEvent event, Object data) {
        Consumer<Object> handler = listeners.get(event);
        if (handler != null) {
            handler.accept(data);
        }
    }

    /**
     * Method ID: MTH-003
     * Clears all registered event handlers. Used during game reset.
     */
    public void reset() {
        listeners.clear();
    }
}