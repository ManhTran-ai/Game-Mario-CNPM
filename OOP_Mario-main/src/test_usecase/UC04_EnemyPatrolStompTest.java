package test_usecase.test_usecase;

import controller.CollisionSystem;
import event.EventBus;
import event.GameEvent;
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
 * Test Case cho UC04 — Enemy Tuần Tra Và Mario Đạp Enemy
 * ─────────────────────────────────────────────────────────────
 * Mục tiêu : Kiểm thử Enemy di chuyển ngang, đảo chiều khi chạm gạch,
 *             và Mario nhảy đạp Enemy từ trên xuống.
 *
 * Các test case :
 *   TC-UC04-001 : Enemy di chuyển ngang → velX != 0
 *   TC-UC04-002 : Enemy đảo chiều 1 lần → velX đảo dấu
 *   TC-UC04-003 : Enemy đảo chiều 2 lần → velX về ban đầu
 *   TC-UC04-004 : Mario đạp Enemy → STOMP_ENEMY event post
 *   TC-UC04-005 : Mario đạp Enemy → Mario nhận điểm
 *   TC-UC04-006 : checkBottomCollisions() với null không crash
 *   TC-UC04-007 : checkAll() xử lý enemy thành công
 *   TC-UC04-008 : Enemy landOnSurface đặt đúng Y
 *   TC-UC04-009 : Enemy.setFalling(true) thay đổi trạng thái
 *   TC-UC04-010 : EventBus post STOMP_ENEMY được nhận
 *
 * Tham chiếu : UC04_Sequence.puml
 */
public class UC04_EnemyPatrolStompTest {

    private CollisionSystem collisionSystem;
    private Map map;
    private Mario mario;
    private boolean stompFired;

    @BeforeEach
    void setUp() {
        EventBus.getInstance().reset();
        collisionSystem = new CollisionSystem();
        map = new Map(300, null);
        assertDoesNotThrow(() -> mario = new Mario(100, 260));
        map.setMario(mario);
        map.addGroundBrick(new GroundBrick(0, 380, null));
        stompFired = false;
        EventBus.getInstance().subscribe(GameEvent.STOMP_ENEMY, d -> stompFired = true);
    }

    // TC-UC04-001
    @Test
    @DisplayName("TC-UC04-001: Turtle di chuyển ngang → velX != 0")
    void tcUc04001_enemyMovesHorizontally() {
        Turtle turtle = new Turtle(200, 300, null);
        assertTrue(turtle.getVelX() != 0,
            "TC-UC04-001: Turtle phải có velX != 0. Actual: " + turtle.getVelX());
    }

    // TC-UC04-002
    @Test
    @DisplayName("TC-UC04-002: Turtle đảo chiều 1 lần → velX đảo dấu")
    void tcUc04002_enemyReversesOnce() {
        Turtle turtle = new Turtle(200, 300, null);
        double before = turtle.getVelX();
        turtle.reverseDirection();
        assertEquals(-before, turtle.getVelX(),
            "TC-UC04-002: velX phải đảo dấu. Before: " + before + " | After: " + turtle.getVelX());
    }

    // TC-UC04-003
    @Test
    @DisplayName("TC-UC04-003: Turtle đảo chiều 2 lần → velX về ban đầu")
    void tcUc04003_enemyReversesTwice() {
        Turtle turtle = new Turtle(200, 300, null);
        double original = turtle.getVelX();
        turtle.reverseDirection();
        turtle.reverseDirection();
        assertEquals(original, turtle.getVelX(),
            "TC-UC04-003: velX phải về giá trị ban đầu. Original: " + original + " | After: " + turtle.getVelX());
    }

    // TC-UC04-004
    @Test
    @DisplayName("TC-UC04-004: Mario đạp Enemy → STOMP_ENEMY event post")
    void tcUc04004_stompPostsEvent() {
        assertDoesNotThrow(() -> {
            EventBus.getInstance().reset();
            boolean[] fired = { false };
            EventBus.getInstance().subscribe(GameEvent.STOMP_ENEMY, d -> fired[0] = true);

            Mario m = new Mario(100, 248);
            m.setVelY(2);
            m.setFalling(true);
            m.setDimension(48, 38);
            map.setMario(m);

            Turtle turtle = new Turtle(100, 276, null);
            turtle.setDimension(new java.awt.Dimension(48, 48));
            map.addEnemy(turtle);

            collisionSystem.checkAll(map, null);

            assertTrue(fired[0],
                "TC-UC04-004: STOMP_ENEMY event phải được post khi Mario đạp Enemy");
        });
    }

    // TC-UC04-005
    @Test
    @DisplayName("TC-UC04-005: Mario đạp Enemy → Mario nhận STOMP_POINTS")
    void tcUc04005_stompAwardsPoints() {
        assertDoesNotThrow(() -> {
            Mario m = new Mario(100, 248);
            m.setVelY(2);
            m.setFalling(true);
            m.setDimension(48, 38);
            map.setMario(m);

            Turtle turtle = new Turtle(100, 276, null);
            turtle.setDimension(new java.awt.Dimension(48, 48));
            map.addEnemy(turtle);
            int pointsBefore = m.getPoints();

            collisionSystem.checkAll(map, null);

            assertTrue(m.getPoints() > pointsBefore,
                "TC-UC04-005: Điểm phải tăng. Before: " + pointsBefore + " | After: " + m.getPoints());
        });
    }

    // TC-UC04-006
    @Test
    @DisplayName("TC-UC04-006: checkAll(null) không crash")
    void tcUc04006_checkAllNull() {
        assertDoesNotThrow(() -> collisionSystem.checkAll(null, null),
            "TC-UC04-006: checkAll(null) không được crash");
    }

    // TC-UC04-007
    @Test
    @DisplayName("TC-UC04-007: checkAll() xử lý enemy thành công")
    void tcUc04007_checkAllWithEnemy() {
        Turtle turtle = new Turtle(100, 276, null);
        map.addEnemy(turtle);

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "TC-UC04-007: checkAll với enemy không được crash");
    }

    // TC-UC04-008
    @Test
    @DisplayName("TC-UC04-008: Enemy.landOnSurface(300) đặt đúng Y")
    void tcUc04008_enemyLandOnSurface() {
        Enemy enemy = new Enemy(200, 250, null);
        enemy.setDimension(new java.awt.Dimension(48, 48));
        enemy.landOnSurface(300);
        assertEquals(300 - 48, (int) enemy.getY(),
            "TC-UC04-008: Y phải bằng 300 - height = " + (300 - 48));
    }

    // TC-UC04-009
    @Test
    @DisplayName("TC-UC04-009: Enemy.setFalling(true) thay đổi trạng thái")
    void tcUc04009_enemySetFalling() {
        Enemy enemy = new Enemy(200, 300, null);
        assertFalse(enemy.isFalling(),
            "Setup: Enemy ban đầu falling = false");
        enemy.setFalling(true);
        assertTrue(enemy.isFalling(),
            "TC-UC04-009: isFalling() phải true sau setFalling(true)");
    }

    // TC-UC04-010
    @Test
    @DisplayName("TC-UC04-010: EventBus post STOMP_ENEMY được nhận")
    void tcUc04010_stompEventReceived() {
        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.STOMP_ENEMY, d -> fired[0] = true);
        EventBus.getInstance().post(GameEvent.STOMP_ENEMY);
        assertTrue(fired[0],
            "TC-UC04-010: STOMP_ENEMY event phải được nhận");
    }
}
