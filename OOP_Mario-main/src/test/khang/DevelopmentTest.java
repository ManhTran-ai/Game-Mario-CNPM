package test.khang;

import event.EventBus;
import event.GameEvent;
import manager.ButtonAction;
import manager.GameStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Development Testing - Người 1 tự kiểm thử code của mình: Khang
 * EventBus, GameEvent, GameStatus, ButtonAction
 *
 * Phạm vi:
 * - Unit test cho từng thành phần
 * - Partition testing (biên, trung tâm)
 * - Guideline-based testing (null, edge)
 *
 * Lưu ý: GameController có constructor private, các test tập trung vào
 * thành phần có thể test trực tiếp: EventBus, GameStatus, ButtonAction.
 */
public class DevelopmentTest {

    // ============================================================
    // EventBus Tests
    // ============================================================

    @Test
    @DisplayName("EventBus - getInstance() trả về cùng instance (Singleton)")
    void eventBus_singletonReturnsSameInstance() {
        EventBus bus1 = EventBus.getInstance();
        EventBus bus2 = EventBus.getInstance();
        assertSame(bus1, bus2,
            "EventBus.getInstance() phải trả về cùng instance");
    }

    @Test
    @DisplayName("EventBus - subscribe và post event không payload")
    void eventBus_subscribeAndPostNoPayload() {
        EventBus bus = EventBus.getInstance();
        StringBuilder received = new StringBuilder();

        bus.subscribe(GameEvent.JUMP, (data) -> received.append("jumped"));
        bus.post(GameEvent.JUMP);

        assertEquals("jumped", received.toString(),
            "Listener phải nhận event JUMP");
    }

    @Test
    @DisplayName("EventBus - subscribe và post event có payload data")
    void eventBus_subscribeAndPostWithPayload() {
        EventBus bus = EventBus.getInstance();
        int[] receivedValue = { -1 };

        bus.subscribe(GameEvent.COIN_COLLECTED, (data) -> receivedValue[0] = (Integer) data);
        bus.post(GameEvent.COIN_COLLECTED, 100);

        assertEquals(100, receivedValue[0],
            "Listener phải nhận payload = 100");
    }

    @Test
    @DisplayName("EventBus - post event không có subscriber không crash")
    void eventBus_postWithoutSubscriberDoesNotCrash() {
        EventBus bus = EventBus.getInstance();
        assertDoesNotThrow(() -> bus.post(GameEvent.MARIO_DIES),
            "post() khi không có subscriber không được crash");
    }

    @Test
    @DisplayName("EventBus - post event null không crash")
    void eventBus_postNullEventDoesNotCrash() {
        EventBus bus = EventBus.getInstance();
        assertDoesNotThrow(() -> bus.post(null),
            "post(null) không được crash");
    }

    @Test
    @DisplayName("EventBus - reset() xóa tất cả subscriber")
    void eventBus_resetClearsAllSubscribers() {
        EventBus bus = EventBus.getInstance();
        StringBuilder received = new StringBuilder();

        bus.subscribe(GameEvent.STOMP_ENEMY, (data) -> received.append("stomp"));
        bus.reset();
        bus.post(GameEvent.STOMP_ENEMY);

        assertEquals("", received.toString(),
            "reset() phải xóa tất cả subscriber");
    }

    @Test
    @DisplayName("EventBus - subscribe nhiều event type khác nhau")
    void eventBus_subscribeMultipleEventTypes() {
        EventBus bus = EventBus.getInstance();
        int[] counters = { 0, 0 };

        bus.subscribe(GameEvent.JUMP, (data) -> counters[0]++);
        bus.subscribe(GameEvent.COIN_COLLECTED, (data) -> counters[1]++);
        bus.post(GameEvent.JUMP);
        bus.post(GameEvent.COIN_COLLECTED);

        assertEquals(1, counters[0], "JUMP counter must be 1");
        assertEquals(1, counters[1], "COIN counter must be 1");
    }

    // ============================================================
    // GameEvent Tests
    // ============================================================

    @Test
    @DisplayName("GameEvent - có đủ các loại event cần thiết (ít nhất 9)")
    void gameEvent_hasAllRequiredEventTypes() {
        GameEvent[] events = GameEvent.values();
        assertTrue(events.length >= 9,
            "GameEvent phải có ít nhất 9 loại event. Actual: " + events.length);
    }

    @Test
    @DisplayName("GameEvent - JUMP tồn tại")
    void gameEvent_jumpExists() {
        assertNotNull(GameEvent.JUMP, "JUMP event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - COIN_COLLECTED tồn tại")
    void gameEvent_coinCollectedExists() {
        assertNotNull(GameEvent.COIN_COLLECTED, "COIN_COLLECTED event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - FIREBALL_FIRED tồn tại")
    void gameEvent_fireballFiredExists() {
        assertNotNull(GameEvent.FIREBALL_FIRED, "FIREBALL_FIRED event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - STOMP_ENEMY tồn tại")
    void gameEvent_stompEnemyExists() {
        assertNotNull(GameEvent.STOMP_ENEMY, "STOMP_ENEMY event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - ONE_UP tồn tại")
    void gameEvent_oneUpExists() {
        assertNotNull(GameEvent.ONE_UP, "ONE_UP event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - SUPER_MUSHROOM tồn tại")
    void gameEvent_superMushroomExists() {
        assertNotNull(GameEvent.SUPER_MUSHROOM, "SUPER_MUSHROOM event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - FIRE_FLOWER tồn tại")
    void gameEvent_fireFlowerExists() {
        assertNotNull(GameEvent.FIRE_FLOWER, "FIRE_FLOWER event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - MARIO_DIES tồn tại")
    void gameEvent_marioDiesExists() {
        assertNotNull(GameEvent.MARIO_DIES, "MARIO_DIES event phải tồn tại");
    }

    @Test
    @DisplayName("GameEvent - CAMERA_SHAKE tồn tại")
    void gameEvent_cameraShakeExists() {
        assertNotNull(GameEvent.CAMERA_SHAKE, "CAMERA_SHAKE event phải tồn tại");
    }

    // ============================================================
    // GameStatus Tests (enum - chỉ test giá trị)
    // ============================================================

    @Test
    @DisplayName("GameStatus - enum có đủ các trạng thái cần thiết (ít nhất 8)")
    void gameStatus_hasAllRequiredStates() {
        GameStatus[] states = GameStatus.values();
        assertTrue(states.length >= 8,
            "GameStatus phải có ít nhất 8 trạng thái. Actual: " + states.length);
    }

    @Test
    @DisplayName("GameStatus - RUNNING tồn tại")
    void gameStatus_runningExists() {
        assertNotNull(GameStatus.RUNNING, "RUNNING state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - PAUSED tồn tại")
    void gameStatus_pausedExists() {
        assertNotNull(GameStatus.PAUSED, "PAUSED state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - GAME_OVER tồn tại")
    void gameStatus_gameOverExists() {
        assertNotNull(GameStatus.GAME_OVER, "GAME_OVER state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - START_SCREEN tồn tại")
    void gameStatus_startScreenExists() {
        assertNotNull(GameStatus.START_SCREEN, "START_SCREEN state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - MAP_SELECTION tồn tại")
    void gameStatus_mapSelectionExists() {
        assertNotNull(GameStatus.MAP_SELECTION, "MAP_SELECTION state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - HELP_SCREEN tồn tại")
    void gameStatus_helpScreenExists() {
        assertNotNull(GameStatus.HELP_SCREEN, "HELP_SCREEN state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - MISSION_PASSED tồn tại")
    void gameStatus_missionPassedExists() {
        assertNotNull(GameStatus.MISSION_PASSED, "MISSION_PASSED state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - ABOUT_SCREEN tồn tại")
    void gameStatus_aboutScreenExists() {
        assertNotNull(GameStatus.ABOUT_SCREEN, "ABOUT_SCREEN state phải tồn tại");
    }

    @Test
    @DisplayName("GameStatus - valueOf hoạt động đúng")
    void gameStatus_valueOfWorks() {
        assertEquals(GameStatus.RUNNING, GameStatus.valueOf("RUNNING"),
            "valueOf('RUNNING') phải trả về RUNNING");
        assertEquals(GameStatus.PAUSED, GameStatus.valueOf("PAUSED"),
            "valueOf('PAUSED') phải trả về PAUSED");
        assertEquals(GameStatus.GAME_OVER, GameStatus.valueOf("GAME_OVER"),
            "valueOf('GAME_OVER') phải trả về GAME_OVER");
    }

    // ============================================================
    // ButtonAction Tests (enum - chỉ test giá trị)
    // ============================================================

    @Test
    @DisplayName("ButtonAction - enum có đủ các action cần thiết (ít nhất 13)")
    void buttonAction_hasAllRequiredActions() {
        ButtonAction[] actions = ButtonAction.values();
        assertTrue(actions.length >= 13,
            "ButtonAction phải có ít nhất 13 action. Actual: " + actions.length);
    }

    @Test
    @DisplayName("ButtonAction - JUMP tồn tại")
    void buttonAction_jumpExists() {
        assertNotNull(ButtonAction.JUMP, "JUMP action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - M_RIGHT tồn tại")
    void buttonAction_mRightExists() {
        assertNotNull(ButtonAction.M_RIGHT, "M_RIGHT action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - M_LEFT tồn tại")
    void buttonAction_mLeftExists() {
        assertNotNull(ButtonAction.M_LEFT, "M_LEFT action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - PAUSE_RESUME tồn tại")
    void buttonAction_pauseResumeExists() {
        assertNotNull(ButtonAction.PAUSE_RESUME, "PAUSE_RESUME action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - FIRE tồn tại")
    void buttonAction_fireExists() {
        assertNotNull(ButtonAction.FIRE, "FIRE action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - SELECT tồn tại")
    void buttonAction_selectExists() {
        assertNotNull(ButtonAction.SELECT, "SELECT action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - GO_UP và GO_DOWN tồn tại")
    void buttonAction_navigationExists() {
        assertNotNull(ButtonAction.GO_UP, "GO_UP action phải tồn tại");
        assertNotNull(ButtonAction.GO_DOWN, "GO_DOWN action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - NO_ACTION tồn tại")
    void buttonAction_noActionExists() {
        assertNotNull(ButtonAction.NO_ACTION, "NO_ACTION phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - CROUCH tồn tại")
    void buttonAction_crouchExists() {
        assertNotNull(ButtonAction.CROUCH, "CROUCH action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - START tồn tại")
    void buttonAction_startExists() {
        assertNotNull(ButtonAction.START, "START action phải tồn tại");
    }

    @Test
    @DisplayName("ButtonAction - GO_TO_START_SCREEN tồn tại")
    void buttonAction_goToStartScreenExists() {
        assertNotNull(ButtonAction.GO_TO_START_SCREEN, "GO_TO_START_SCREEN phải tồn tại");
    }

    // ============================================================
    // Integration: EventBus + GameEvent
    // ============================================================

    @Test
    @DisplayName("Integration - EventBus phát STOMP_ENEMY event")
    void integration_eventBusEmitsStompEnemy() {
        EventBus bus = EventBus.getInstance();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.STOMP_ENEMY, (d) -> fired[0] = true);

        bus.post(GameEvent.STOMP_ENEMY);

        assertTrue(fired[0], "STOMP_ENEMY phải được nhận");
    }

    @Test
    @DisplayName("Integration - EventBus phát CAMERA_SHAKE event")
    void integration_eventBusEmitsCameraShake() {
        EventBus bus = EventBus.getInstance();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.CAMERA_SHAKE, (d) -> fired[0] = true);

        bus.post(GameEvent.CAMERA_SHAKE);

        assertTrue(fired[0], "CAMERA_SHAKE phải được nhận");
    }

    @Test
    @DisplayName("Integration - EventBus phát FIREBALL_FIRED event")
    void integration_eventBusEmitsFireballFired() {
        EventBus bus = EventBus.getInstance();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.FIREBALL_FIRED, (d) -> fired[0] = true);

        bus.post(GameEvent.FIREBALL_FIRED);

        assertTrue(fired[0], "FIREBALL_FIRED phải được nhận");
    }

    @Test
    @DisplayName("Integration - EventBus phát SUPER_MUSHROOM event")
    void integration_eventBusEmitsSuperMushroom() {
        EventBus bus = EventBus.getInstance();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.SUPER_MUSHROOM, (d) -> fired[0] = true);

        bus.post(GameEvent.SUPER_MUSHROOM);

        assertTrue(fired[0], "SUPER_MUSHROOM phải được nhận");
    }

    @Test
    @DisplayName("Integration - EventBus phát FIRE_FLOWER event")
    void integration_eventBusEmitsFireFlower() {
        EventBus bus = EventBus.getInstance();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.FIRE_FLOWER, (d) -> fired[0] = true);

        bus.post(GameEvent.FIRE_FLOWER);

        assertTrue(fired[0], "FIRE_FLOWER phải được nhận");
    }

    @Test
    @DisplayName("Integration - EventBus phát ONE_UP event")
    void integration_eventBusEmitsOneUp() {
        EventBus bus = EventBus.getInstance();
        boolean[] fired = { false };
        bus.subscribe(GameEvent.ONE_UP, (d) -> fired[0] = true);

        bus.post(GameEvent.ONE_UP);

        assertTrue(fired[0], "ONE_UP phải được nhận");
    }

    // ============================================================
    // Guideline-based
    // ============================================================

    @Test
    @DisplayName("Guideline - EventBus với payload null không crash")
    void guideline_eventBusPostNullPayloadDoesNotCrash() {
        EventBus bus = EventBus.getInstance();
        assertDoesNotThrow(() -> bus.post(GameEvent.COIN_COLLECTED, null),
            "post(event, null) không được crash");
    }

    @Test
    @DisplayName("Guideline - EventBus subscribe cùng event 2 lần gọi cả 2 handler")
    void guideline_eventBusCallsAllHandlersForSameEvent() {
        EventBus bus = EventBus.getInstance();
        int[] count = { 0 };
        bus.subscribe(GameEvent.JUMP, (d) -> count[0]++);
        bus.subscribe(GameEvent.JUMP, (d) -> count[0]++);
        bus.post(GameEvent.JUMP);

        assertEquals(2, count[0],
            "Cả 2 handler phải được gọi khi post JUMP");
    }
}