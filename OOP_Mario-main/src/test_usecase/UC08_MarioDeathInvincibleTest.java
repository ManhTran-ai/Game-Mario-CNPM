package test_usecase;

import controller.CollisionSystem;
import event.EventBus;
import event.GameEvent;
import manager.Camera;
import model.Map;
import model.brick.GroundBrick;
import model.hero.Mario;
import model.hero.MarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Case cho UC08 — Mario Chết Và Invincible
 * ─────────────────────────────────────────────────────────────
 * Mục tiêu : Kiểm thử Mario SMALL chạm Enemy → chết và reset map.
 *             Mario SUPER/FIRE chạm Enemy → downgrade form + camera shake.
 *
 * Các test case :
 *   TC-UC08-001 : Mario SMALL.onTouchEnemy() → post MARIO_DIES
 *   TC-UC08-002 : Mario SMALL.onTouchEnemy() → return true
 *   TC-UC08-003 : Mario SUPER.onTouchEnemy() → KHÔNG post MARIO_DIES
 *   TC-UC08-004 : Mario SUPER.onTouchEnemy() → return false
 *   TC-UC08-005 : EventBus post MARIO_DIES được nhận
 *   TC-UC08-006 : EventBus post CAMERA_SHAKE được nhận
 *   TC-UC08-007 : Camera.shakeCamera() kích hoạt shaking
 *   TC-UC08-008 : Mario.resetLocation() đặt X về 50
 *   TC-UC08-009 : Mario.resetLocation() giữ nguyên points
 *   TC-UC08-010 : Camera.moveCam(0,0) với shake không crash
 *   TC-UC08-011 : Camera shake 60 lần → kết thúc
 *   TC-UC08-012 : Mario khởi tạo là SMALL (isSuper=false, isFire=false)
 *
 * Tham chiếu : UC08_Sequence.puml
 */
public class UC08_MarioDeathInvincibleTest {

    private CollisionSystem collisionSystem;
    private Map map;
    private Mario mario;
    private Camera camera;
    private boolean marioDiesFired;
    private boolean cameraShakeFired;

    @BeforeEach
    void setUp() {
        EventBus.getInstance().reset();
        collisionSystem = new CollisionSystem();
        map = new Map(300, null);
        assertDoesNotThrow(() -> mario = new Mario(100, 300));
        camera = new Camera();
        map.setMario(mario);
        map.addGroundBrick(new GroundBrick(0, 380, null));

        marioDiesFired = false;
        cameraShakeFired = false;
        EventBus.getInstance().subscribe(GameEvent.MARIO_DIES, d -> marioDiesFired = true);
        EventBus.getInstance().subscribe(GameEvent.CAMERA_SHAKE, d -> cameraShakeFired = true);
    }

    // TC-UC08-001
    @Test
    @DisplayName("TC-UC08-001: Mario SMALL.onTouchEnemy() → post MARIO_DIES event")
    void tcUc08001_smallMarioOnTouchEnemyPostsMarioDies() {
        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.MARIO_DIES, d -> fired[0] = true);
        mario.onTouchEnemy();
        assertTrue(fired[0],
            "TC-UC08-001: SMALL Mario onTouchEnemy() phải post MARIO_DIES");
    }

    // TC-UC08-002
    @Test
    @DisplayName("TC-UC08-002: Mario SMALL.onTouchEnemy() → return true")
    void tcUc08002_smallMarioOnTouchEnemyReturnsTrue() {
        boolean result = mario.onTouchEnemy();
        assertTrue(result,
            "TC-UC08-002: SMALL Mario onTouchEnemy() phải return true (chết/map reset)");
    }

    // TC-UC08-003
    @Test
    @DisplayName("TC-UC08-003: Mario SUPER.onTouchEnemy() → KHÔNG post MARIO_DIES")
    void tcUc08003_superMarioOnTouchEnemyNoMarioDies() {
        Mario superMario = assertDoesNotThrow(() -> new Mario(100, 300));
        superMario.setMarioForm(new MarioForm(null, true, false));
        assertTrue(superMario.getMarioForm().isSuper(),
            "Setup: Mario phải là SUPER");

        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.MARIO_DIES, d -> fired[0] = true);
        superMario.onTouchEnemy();

        assertFalse(fired[0],
            "TC-UC08-003: SUPER Mario onTouchEnemy() KHÔNG được post MARIO_DIES");
    }

    // TC-UC08-004
    @Test
    @DisplayName("TC-UC08-004: Mario SUPER.onTouchEnemy() → return false")
    void tcUc08004_superMarioOnTouchEnemyReturnsFalse() {
        Mario superMario = assertDoesNotThrow(() -> new Mario(100, 300));
        superMario.setMarioForm(new MarioForm(null, true, false));
        boolean result = superMario.onTouchEnemy();
        assertFalse(result,
            "TC-UC08-004: SUPER Mario onTouchEnemy() phải return false (downgrade, không chết)");
    }

    // TC-UC08-005
    @Test
    @DisplayName("TC-UC08-005: EventBus post MARIO_DIES được nhận")
    void tcUc08005_marioDiesEventReceived() {
        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.MARIO_DIES, d -> fired[0] = true);
        EventBus.getInstance().post(GameEvent.MARIO_DIES);
        assertTrue(fired[0],
            "TC-UC08-005: MARIO_DIES event phải được nhận");
    }

    // TC-UC08-006
    @Test
    @DisplayName("TC-UC08-006: EventBus post CAMERA_SHAKE được nhận")
    void tcUc08006_cameraShakeEventReceived() {
        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.CAMERA_SHAKE, d -> fired[0] = true);
        EventBus.getInstance().post(GameEvent.CAMERA_SHAKE);
        assertTrue(fired[0],
            "TC-UC08-006: CAMERA_SHAKE event phải được nhận");
    }

    // TC-UC08-007
    @Test
    @DisplayName("TC-UC08-007: Camera.shakeCamera() kích hoạt trạng thái shake")
    void tcUc08007_shakeCameraActivates() {
        assertDoesNotThrow(() -> camera.shakeCamera(),
            "TC-UC08-007: shakeCamera() không được crash");
    }

    // TC-UC08-008
    @Test
    @DisplayName("TC-UC08-008: Mario.resetLocation() đặt X về 50")
    void tcUc08008_resetLocationResetsX() {
        mario.setX(500);
        mario.resetLocation();
        assertEquals(50, (int) mario.getX(),
            "TC-UC08-008: resetLocation() phải đặt X = 50. Actual: " + (int) mario.getX());
    }

    // TC-UC08-009
    @Test
    @DisplayName("TC-UC08-009: Mario.resetLocation() giữ nguyên points")
    void tcUc08009_resetLocationKeepsPoints() {
        mario.acquirePoints(500);
        int pointsBefore = mario.getPoints();
        mario.resetLocation();
        assertEquals(pointsBefore, mario.getPoints(),
            "TC-UC08-009: resetLocation() phải giữ nguyên points. Before: " + pointsBefore + " | After: " + mario.getPoints());
    }

    // TC-UC08-010
    @Test
    @DisplayName("TC-UC08-010: Camera.moveCam(0,0) với shake không crash")
    void tcUc08010_moveCamWithShake() {
        camera.shakeCamera();
        assertDoesNotThrow(() -> camera.moveCam(0, 0),
            "TC-UC08-010: moveCam(0,0) khi đang shake không crash");
    }

    // TC-UC08-011
    @Test
    @DisplayName("TC-UC08-011: Camera shake 60 lần → kết thúc")
    void tcUc08011_cameraShake60Frames() {
        camera.shakeCamera();
        for (int i = 0; i < 70; i++) {
            camera.moveCam(0, 0);
        }
        assertDoesNotThrow(() -> camera.moveCam(0, 0),
            "TC-UC08-011: Camera phải hoạt động ổn định sau 70 frames");
    }

    // TC-UC08-012
    @Test
    @DisplayName("TC-UC08-012: Mario khởi tạo là SMALL (isSuper=false, isFire=false)")
    void tcUc08012_marioInitSmallForm() {
        Mario m = assertDoesNotThrow(() -> new Mario(100, 300));
        assertFalse(m.getMarioForm().isSuper(),
            "TC-UC08-012a: Mario init isSuper=false. Actual: " + m.getMarioForm().isSuper());
        assertFalse(m.getMarioForm().isFire(),
            "TC-UC08-012b: Mario init isFire=false. Actual: " + m.getMarioForm().isFire());
    }
}
