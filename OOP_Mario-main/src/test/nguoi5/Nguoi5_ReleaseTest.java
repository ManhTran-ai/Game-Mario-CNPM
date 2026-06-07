package test.test.nguoi5;

import event.EventBus;
import event.GameEvent;
import manager.ButtonAction;
import manager.Camera;
import manager.GameStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Release Testing - Người 5 kiểm thử domain của Người 1:
 * EventBus, GameEvent, GameStatus, ButtonAction, Camera
 *
 * Mục đích: Xác nhận hệ thống điều khiển, trạng thái game, event hoạt động đúng.
 *
 * Chú ý:
 *   EventBus là Singleton → dùng getInstance()
 *   reset() được gọi trước mỗi test để tránh interference
 */
public class Nguoi5_ReleaseTest {

    // ============================================================
    // 1. EventBus Tests
    // ============================================================

    @Test
    @DisplayName("EB-001: EventBus.getInstance() returns instance")
    void eventBus_getInstance() {
        EventBus bus = EventBus.getInstance();
        assertNotNull(bus, "getInstance() must return instance");
    }

    @Test
    @DisplayName("EB-002: EventBus is Singleton")
    void eventBus_isSingleton() {
        EventBus bus1 = EventBus.getInstance();
        EventBus bus2 = EventBus.getInstance();
        assertSame(bus1, bus2, "getInstance() must return same instance");
    }

    @Test
    @DisplayName("EB-003: EventBus - subscribe and post(JUMP) calls handler")
    void eventBus_subscribeAndPostJump() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        StringBuilder sb = new StringBuilder();

        bus.subscribe(GameEvent.JUMP, d -> sb.append("JUMP"));
        bus.post(GameEvent.JUMP);

        assertEquals("JUMP", sb.toString(), "Handler must receive JUMP event");
    }

    @Test
    @DisplayName("EB-004: EventBus - post with payload passes data correctly")
    void eventBus_postWithPayload() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        int[] val = { -1 };

        bus.subscribe(GameEvent.COIN_COLLECTED, d -> val[0] = (Integer) d);
        bus.post(GameEvent.COIN_COLLECTED, 42);

        assertEquals(42, val[0], "Payload 42 must be passed correctly");
    }

    @Test
    @DisplayName("EB-005: EventBus - post(STOMP_ENEMY) calls handler")
    void eventBus_postStompEnemy() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.STOMP_ENEMY, d -> fired[0] = true);

        bus.post(GameEvent.STOMP_ENEMY);

        assertTrue(fired[0], "Handler must receive STOMP_ENEMY");
    }

    @Test
    @DisplayName("EB-006: EventBus - post(MARIO_DIES) calls handler")
    void eventBus_postMarioDies() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.MARIO_DIES, d -> fired[0] = true);

        bus.post(GameEvent.MARIO_DIES);

        assertTrue(fired[0], "Handler must receive MARIO_DIES");
    }

    @Test
    @DisplayName("EB-007: EventBus - post(CAMERA_SHAKE) calls handler")
    void eventBus_postCameraShake() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.CAMERA_SHAKE, d -> fired[0] = true);

        bus.post(GameEvent.CAMERA_SHAKE);

        assertTrue(fired[0], "Handler must receive CAMERA_SHAKE");
    }

    @Test
    @DisplayName("EB-008: EventBus - post(FIREBALL_FIRED) calls handler")
    void eventBus_postFireballFired() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.FIREBALL_FIRED, d -> fired[0] = true);

        bus.post(GameEvent.FIREBALL_FIRED);

        assertTrue(fired[0], "Handler must receive FIREBALL_FIRED");
    }

    @Test
    @DisplayName("EB-009: EventBus - post(SUPER_MUSHROOM) calls handler")
    void eventBus_postSuperMushroom() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.SUPER_MUSHROOM, d -> fired[0] = true);

        bus.post(GameEvent.SUPER_MUSHROOM);

        assertTrue(fired[0], "Handler must receive SUPER_MUSHROOM");
    }

    @Test
    @DisplayName("EB-010: EventBus - post(FIRE_FLOWER) calls handler")
    void eventBus_postFireFlower() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.FIRE_FLOWER, d -> fired[0] = true);

        bus.post(GameEvent.FIRE_FLOWER);

        assertTrue(fired[0], "Handler must receive FIRE_FLOWER");
    }

    @Test
    @DisplayName("EB-011: EventBus - post(ONE_UP) calls handler")
    void eventBus_postOneUp() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.ONE_UP, d -> fired[0] = true);

        bus.post(GameEvent.ONE_UP);

        assertTrue(fired[0], "Handler must receive ONE_UP");
    }

    @Test
    @DisplayName("EB-012: EventBus - reset() clears subscribers")
    void eventBus_resetClearsSubscribers() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.JUMP, d -> fired[0] = true);

        bus.reset();
        bus.post(GameEvent.JUMP);

        assertFalse(fired[0], "reset() must clear subscribers");
    }

    @Test
    @DisplayName("EB-013: EventBus - post without subscription doesn't crash")
    void eventBus_postWithoutSubscription() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        assertDoesNotThrow(() -> bus.post(GameEvent.COIN_COLLECTED),
            "post() without subscription must not crash");
    }

    @Test
    @DisplayName("EB-014: EventBus - post(null) doesn't crash")
    void eventBus_postNullEvent() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        assertDoesNotThrow(() -> bus.post(null),
            "post(null) must not crash");
    }

    // ============================================================
    // 2. GameStatus Tests
    // ============================================================

    @Test
    @DisplayName("GS-001: GameStatus.RUNNING exists")
    void gameStatus_runningExists() {
        assertNotNull(GameStatus.RUNNING, "RUNNING must exist");
    }

    @Test
    @DisplayName("GS-002: GameStatus.PAUSED exists")
    void gameStatus_pausedExists() {
        assertNotNull(GameStatus.PAUSED, "PAUSED must exist");
    }

    @Test
    @DisplayName("GS-003: GameStatus.GAME_OVER exists")
    void gameStatus_gameOverExists() {
        assertNotNull(GameStatus.GAME_OVER, "GAME_OVER must exist");
    }

    @Test
    @DisplayName("GS-004: GameStatus.START_SCREEN exists")
    void gameStatus_startScreenExists() {
        assertNotNull(GameStatus.START_SCREEN, "START_SCREEN must exist");
    }

    @Test
    @DisplayName("GS-005: GameStatus.MAP_SELECTION exists")
    void gameStatus_mapSelectionExists() {
        assertNotNull(GameStatus.MAP_SELECTION, "MAP_SELECTION must exist");
    }

    @Test
    @DisplayName("GS-006: GameStatus.HELP_SCREEN exists")
    void gameStatus_helpScreenExists() {
        assertNotNull(GameStatus.HELP_SCREEN, "HELP_SCREEN must exist");
    }

    @Test
    @DisplayName("GS-007: GameStatus.MISSION_PASSED exists")
    void gameStatus_missionPassedExists() {
        assertNotNull(GameStatus.MISSION_PASSED, "MISSION_PASSED must exist");
    }

    @Test
    @DisplayName("GS-008: GameStatus.ABOUT_SCREEN exists")
    void gameStatus_aboutScreenExists() {
        assertNotNull(GameStatus.ABOUT_SCREEN, "ABOUT_SCREEN must exist");
    }

    @Test
    @DisplayName("GS-009: GameStatus.values() has at least 8 values")
    void gameStatus_valuesHasAtLeast8() {
        GameStatus[] all = GameStatus.values();
        assertTrue(all.length >= 8,
            "GameStatus must have at least 8 values. Actual: " + all.length);
    }

    // ============================================================
    // 3. ButtonAction Tests
    // ============================================================

    @Test
    @DisplayName("BA-001: ButtonAction.JUMP exists")
    void buttonAction_jumpExists() {
        assertNotNull(ButtonAction.JUMP, "JUMP must exist");
    }

    @Test
    @DisplayName("BA-002: ButtonAction.M_RIGHT exists")
    void buttonAction_mRightExists() {
        assertNotNull(ButtonAction.M_RIGHT, "M_RIGHT must exist");
    }

    @Test
    @DisplayName("BA-003: ButtonAction.M_LEFT exists")
    void buttonAction_mLeftExists() {
        assertNotNull(ButtonAction.M_LEFT, "M_LEFT must exist");
    }

    @Test
    @DisplayName("BA-004: ButtonAction.PAUSE_RESUME exists")
    void buttonAction_pauseResumeExists() {
        assertNotNull(ButtonAction.PAUSE_RESUME, "PAUSE_RESUME must exist");
    }

    @Test
    @DisplayName("BA-005: ButtonAction.FIRE exists")
    void buttonAction_fireExists() {
        assertNotNull(ButtonAction.FIRE, "FIRE must exist");
    }

    @Test
    @DisplayName("BA-006: ButtonAction.SELECT exists")
    void buttonAction_selectExists() {
        assertNotNull(ButtonAction.SELECT, "SELECT must exist");
    }

    @Test
    @DisplayName("BA-007: ButtonAction.NO_ACTION exists")
    void buttonAction_noActionExists() {
        assertNotNull(ButtonAction.NO_ACTION, "NO_ACTION must exist");
    }

    @Test
    @DisplayName("BA-008: ButtonAction.values() has at least 13 values")
    void buttonAction_valuesHasAtLeast13() {
        ButtonAction[] all = ButtonAction.values();
        assertTrue(all.length >= 13,
            "ButtonAction must have at least 13 values. Actual: " + all.length);
    }

    // ============================================================
    // 4. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("SCN-001: EventBus posts multiple different events in order")
    void scenario_multipleEventsInOrder() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        StringBuilder sb = new StringBuilder();

        bus.subscribe(GameEvent.JUMP, d -> sb.append("1"));
        bus.subscribe(GameEvent.COIN_COLLECTED, d -> sb.append("2"));
        bus.subscribe(GameEvent.STOMP_ENEMY, d -> sb.append("3"));

        bus.post(GameEvent.JUMP);
        bus.post(GameEvent.COIN_COLLECTED);
        bus.post(GameEvent.STOMP_ENEMY);

        assertEquals("123", sb.toString(),
            "Events must be received in correct order");
    }

    @Test
    @DisplayName("SCN-002: EventBus calls all subscribers for same event")
    void scenario_allSubscribersCalled() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        int[] count = { 0 };

        bus.subscribe(GameEvent.JUMP, d -> count[0]++);
        bus.subscribe(GameEvent.JUMP, d -> count[0]++);
        bus.subscribe(GameEvent.JUMP, d -> count[0]++);

        bus.post(GameEvent.JUMP);

        assertEquals(3, count[0],
            "All 3 subscribers must be called");
    }

    // ============================================================
    // 5. Camera Tests
    // ============================================================

    @Test
    @DisplayName("CAM-001: Camera - at negative coordinates")
    void camera_negativeCoordinates() {
        Camera cam = new Camera();
        cam.setX(-100);
        cam.setY(-50);
        assertTrue(cam.getX() < 0, "Camera can have negative X");
        assertTrue(cam.getY() < 0, "Camera can have negative Y");
    }

    @Test
    @DisplayName("CAM-002: Camera - shakeCamera doesn't crash")
    void camera_shakeCameraDoesNotCrash() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> cam.shakeCamera(),
            "shakeCamera() must not crash");
    }

    @Test
    @DisplayName("CAM-003: Camera - moveCam doesn't crash")
    void camera_moveCamDoesNotCrash() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> cam.moveCam(10, 10),
            "moveCam() must not crash");
    }

    @Test
    @DisplayName("CAM-004: Camera - shakeCamera and moveCam multiple times don't crash")
    void camera_multipleShakesAndMoves() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> {
            cam.shakeCamera();
            for (int i = 0; i < 10; i++) {
                cam.moveCam(0, 0);
            }
        }, "Multiple shakeCamera and moveCam calls must not crash");
    }

    // ============================================================
    // 6. Guideline-based Testing (Edge Cases & Robustness)
    // ============================================================

    @Test
    @DisplayName("GL-001: EventBus.post(event, null) doesn't crash")
    void guideline_postNullPayload() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        bus.subscribe(GameEvent.JUMP, d -> {});
        assertDoesNotThrow(() -> bus.post(GameEvent.JUMP, null),
            "post(event, null) must not crash");
    }

    @Test
    @DisplayName("GL-002: Camera shake multiple times doesn't crash")
    void guideline_cameraShakeMultipleTimes() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10; i++) {
                cam.shakeCamera();
            }
        }, "Multiple shakeCamera calls must not crash");
    }

    @Test
    @DisplayName("GL-003: EventBus.post 1000 times fast")
    void guideline_eventBusPost1000Times() {
        EventBus bus = EventBus.getInstance();
        bus.reset();
        bus.subscribe(GameEvent.JUMP, d -> {});

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 1000; i++) {
                bus.post(GameEvent.JUMP);
            }
        }, "1000 post() calls must not crash");
    }
}
