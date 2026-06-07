package test_usecase;

import controller.CollisionSystem;
import event.EventBus;
import manager.Camera;
import manager.GameConstants;
import model.Map;
import model.brick.GroundBrick;
import model.Enemy.Enemy;
import model.Enemy.Turtle;
import model.hero.Mario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Case cho UC03 — Mario Di Chuyển Và Va Chạm Ngang
 * ─────────────────────────────────────────────────────────────
 * Mục tiêu : Kiểm thử luồng Mario điều khiển sang trái/phải,
 *             phát hiện và xử lý va chạm ngang với Brick và Enemy.
 *
 * Các test case :
 *   TC-UC03-001 : Mario đi phải → velX > 0
 *   TC-UC03-002 : Mario đi trái khi x > camera.x → velX < 0
 *   TC-UC03-003 : Mario đi trái khi x <= camera.x → velX = 0 (clamp)
 *   TC-UC03-004 : CollisionSystem.checkAll() với map hợp lệ
 *   TC-UC03-005 : CollisionSystem.checkAll() với null không crash
 *   TC-UC03-006 : Mario.landOnSurface() đặt đúng Y
 *   TC-UC03-007 : Mario.hitCeiling() thay đổi Y
 *   TC-UC03-008 : Mario.stopAtX() đặt X và velX
 *   TC-UC03-009 : Mario.stopAtLeftBoundary() clamp khi velX < 0
 *   TC-UC03-010 : GameObject.getBounds() khác null
 *   TC-UC03-011 : Enemy đảo chiều → velX đảo dấu
 *   TC-UC03-012 : GroundBrick đúng position
 *
 * Tham chiếu : UC03_Sequence.puml
 */
public class UC03_MarioMovementCollisionTest {

    private CollisionSystem collisionSystem;
    private Map map;
    private Mario mario;
    private Camera camera;

    @BeforeEach
    void setUp() {
        EventBus.getInstance().reset();
        collisionSystem = new CollisionSystem();
        map = new Map(300, null);
        assertDoesNotThrow(() -> mario = new Mario(100, 300));
        camera = new Camera();
        map.setMario(mario);
    }

    // TC-UC03-001
    @Test
    @DisplayName("TC-UC03-001: Mario đi phải → velX > 0")
    void tcUc03001_marioMoveRightVelXPositive() {
        mario.move(true, camera);
        assertTrue(mario.getVelX() > 0,
            "TC-UC03-001: Mario đi phải có velX > 0. Actual: " + mario.getVelX());
    }

    // TC-UC03-002
    @Test
    @DisplayName("TC-UC03-002: Mario đi trái khi x > camera.x → velX < 0")
    void tcUc03002_marioMoveLeftVelXNegative() {
        camera.setX(50);
        mario.setX(100);
        mario.move(false, camera);
        assertTrue(mario.getVelX() < 0,
            "TC-UC03-002: Mario đi trái khi x > camera.x phải có velX < 0. Actual: " + mario.getVelX());
    }

    // TC-UC03-003
    @Test
    @DisplayName("TC-UC03-003: Mario đi trái khi x <= camera.x → velX = 0 (clamp)")
    void tcUc03003_marioLeftClampedAtBoundary() {
        camera.setX(100);
        mario.setX(100);
        mario.move(false, camera);
        assertEquals(0, mario.getVelX(),
            "TC-UC03-003: Mario đi trái khi x == camera.x phải có velX = 0");
    }

    // TC-UC03-004
    @Test
    @DisplayName("TC-UC03-004: checkAll() với map hợp lệ không crash")
    void tcUc03004_checkAllWithValidMap() {
        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "TC-UC03-004: checkAll với map hợp lệ không được crash");
    }

    // TC-UC03-005
    @Test
    @DisplayName("TC-UC03-005: checkAll() với null không crash")
    void tcUc03005_checkAllWithNull() {
        assertDoesNotThrow(() -> collisionSystem.checkAll(null, null),
            "TC-UC03-005: checkAll(null, null) không được crash");
    }

    // TC-UC03-006
    @Test
    @DisplayName("TC-UC03-006: landOnSurface(300) đặt Mario trên surface y=300")
    void tcUc03006_landOnSurface() {
        int height = GameConstants.SMALL_MARIO_HEIGHT;
        mario.setDimension(GameConstants.SMALL_MARIO_WIDTH, height);
        mario.landOnSurface(300);
        assertEquals(300 - height, (int) mario.getY(),
            "TC-UC03-006: Y phải bằng 300 - height = " + (300 - height));
    }

    // TC-UC03-007
    @Test
    @DisplayName("TC-UC03-007: landOnSurface(200) thay đổi Y của Mario")
    void tcUc03007_landOnSurfaceChangesY() {
        mario.setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);
        double yBefore = mario.getY();
        mario.landOnSurface(200);
        assertTrue(mario.getY() < yBefore,
            "TC-UC03-007: Y phải thay đổi sau landOnSurface. Before: " + yBefore + " | After: " + mario.getY());
    }

    // TC-UC03-008
    @Test
    @DisplayName("TC-UC03-008: stopAtX(150) đặt X=150 và velX=0")
    void tcUc03008_stopAtX() {
        mario.stopAtX(150);
        assertEquals(150, (int) mario.getX(),
            "TC-UC03-008a: X phải bằng 150. Actual: " + (int) mario.getX());
        assertEquals(0, mario.getVelX(),
            "TC-UC03-008b: velX phải bằng 0. Actual: " + mario.getVelX());
    }

    // TC-UC03-009
    @Test
    @DisplayName("TC-UC03-009: stopAtLeftBoundary(200) clamp khi velX < 0")
    void tcUc03009_stopAtLeftBoundary() {
        mario.setVelX(-5);
        mario.stopAtLeftBoundary(200);
        assertEquals(200, (int) mario.getX(),
            "TC-UC03-009a: X phải bằng 200. Actual: " + (int) mario.getX());
        assertEquals(0, mario.getVelX(),
            "TC-UC03-009b: velX phải bằng 0. Actual: " + mario.getVelX());
    }

    // TC-UC03-010
    @Test
    @DisplayName("TC-UC03-010: Mario.getBounds() không null")
    void tcUc03010_getBoundsNotNull() {
        assertNotNull(mario.getBounds(),
            "TC-UC03-010: getBounds() không được null");
    }

    // TC-UC03-011
    @Test
    @DisplayName("TC-UC03-011: Turtle đảo chiều → velX đảo dấu")
    void tcUc03011_enemyReverseDirection() {
        Turtle turtle = new Turtle(200, 300, null);
        double velBefore = turtle.getVelX();
        assertTrue(velBefore != 0, "Setup: Turtle velX phải khác 0. Actual: " + velBefore);
        turtle.reverseDirection();
        assertEquals(-velBefore, turtle.getVelX(),
            "TC-UC03-011: velX phải đảo dấu. Before: " + velBefore + " | After: " + turtle.getVelX());
    }

    // TC-UC03-012
    @Test
    @DisplayName("TC-UC03-012: GroundBrick đặt đúng vị trí")
    void tcUc03012_groundBrickPosition() {
        GroundBrick gb = new GroundBrick(0, 380, null);
        assertEquals(0, (int) gb.getX(),
            "TC-UC03-012a: X phải bằng 0");
        assertEquals(380, (int) gb.getY(),
            "TC-UC03-012b: Y phải bằng 380");
    }
}
