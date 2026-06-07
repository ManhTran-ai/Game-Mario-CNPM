package test.nguoi2;

import controller.CollisionSystem;
import event.EventBus;
import event.GameEvent;
import manager.Camera;
import manager.GameConstants;
import model.EndFlag;
import model.Map;
import model.brick.GroundBrick;
import model.Enemy.Enemy;
import model.Enemy.Turtle;
import model.hero.Fireball;
import model.hero.Mario;
import model.hero.MarioForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Release Testing - Người 2 kiểm thử domain của Người 3:
 * Mario, Enemy, Turtle, EndFlag, Fireball
 *
 * Mục đích: Xác nhận nhân vật và enemy hoạt động đúng trong điều kiện tích hợp.
 */
public class ReleaseTest {

    // ============================================================
    // 1. Mario Tests
    // ============================================================

    @Test
    @DisplayName("Mario - khởi tạo với tọa độ đúng")
    void mario_initAtCorrectPosition() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertEquals(100, mario.getX(), "Mario X phải bằng 100");
            assertEquals(300, mario.getY(), "Mario Y phải bằng 300");
        });
    }

    @Test
    @DisplayName("Mario - move right đặt velX > 0")
    void mario_moveRightSetsVelXPositive() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            Camera cam = new Camera();
            mario.move(true, cam);
            assertTrue(mario.getVelX() > 0,
                "Mario đi phải có velX > 0");
        });
    }

    @Test
    @DisplayName("Mario - move left khi x > camera.x đặt velX < 0")
    void mario_moveLeftWhenAheadOfCameraSetsVelXNegative() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(500, 300);
            Camera cam = new Camera();
            cam.setX(100);
            mario.move(false, cam);
            assertTrue(mario.getVelX() < 0,
                "Mario đi trái khi x > camera.x phải có velX < 0");
        });
    }

    @Test
    @DisplayName("Mario - jump() thay đổi trạng thái khi grounded")
    void mario_jumpChangesStateWhenGrounded() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertTrue(mario.isFalling(), "Mario mới tạo đang falling");
            mario.jump();
            assertTrue(mario.isJumping(),
                "jump() phải đặt isJumping = true khi grounded");
        });
    }

    @Test
    @DisplayName("Mario - acquirePoints tăng điểm")
    void mario_acquirePointsIncreasesScore() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            int before = mario.getPoints();
            mario.acquirePoints(100);
            assertEquals(before + 100, mario.getPoints(),
                "acquirePoints(100) phải tăng điểm thêm 100");
        });
    }

    @Test
    @DisplayName("Mario - acquireCoin tăng coin count")
    void mario_acquireCoinIncrementsCoins() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            int before = mario.getCoins();
            mario.acquireCoin();
            assertEquals(before + 1, mario.getCoins(),
                "acquireCoin() phải tăng coins thêm 1");
        });
    }

    @Test
    @DisplayName("Mario - setMarioForm thay đổi form")
    void mario_setMarioFormChangesForm() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertFalse(mario.getMarioForm().isSuper(),
                "Mario ban đầu không phải Super");
            assertFalse(mario.getMarioForm().isFire(),
                "Mario ban đầu không phải Fire");

            MarioForm newForm = new MarioForm(null, true, false);
            mario.setMarioForm(newForm);
            assertTrue(mario.getMarioForm().isSuper(),
                "Sau setMarioForm(true,false) phải là Super");
        });
    }

    @Test
    @DisplayName("Mario - isSuper() trả đúng giá trị")
    void mario_isSuperReturnsCorrectValue() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertFalse(mario.isSuper(),
                "Mario mới tạo không phải Super");

            MarioForm superForm = new MarioForm(null, true, false);
            mario.setMarioForm(superForm);
            assertTrue(mario.isSuper(),
                "Sau khi set Super form, isSuper() phải trả true");
        });
    }

    @Test
    @DisplayName("Mario - resetLocation() đặt X=50, velX=0, velY=0")
    void mario_resetLocationSetsCorrectValues() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(500, 200);
            mario.setVelX(10);
            mario.setVelY(5);
            mario.setJumping(true);
            mario.setFalling(false);

            mario.resetLocation();

            assertEquals(50, mario.getX(), "resetLocation() phải đặt X = 50");
            assertEquals(0, mario.getVelX(), "resetLocation() phải đặt velX = 0");
            assertEquals(0, mario.getVelY(), "resetLocation() phải đặt velY = 0");
            assertFalse(mario.isJumping(), "resetLocation() phải đặt jumping = false");
            assertTrue(mario.isFalling(), "resetLocation() phải đặt falling = true");
        });
    }

    @Test
    @DisplayName("Mario - stopAtX() đặt X và velX = 0")
    void mario_stopAtXSetsXAndVelX() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            mario.setVelX(5);
            mario.stopAtX(200);
            assertEquals(200, mario.getX(), "stopAtX(200) phải đặt X = 200");
            assertEquals(0, mario.getVelX(), "stopAtX() phải đặt velX = 0");
        });
    }

    @Test
    @DisplayName("Mario - stopAtLeftBoundary() chỉ đặt X khi velX < 0")
    void mario_stopAtLeftBoundaryOnlyWhenVelXNegative() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            mario.setVelX(-5);
            double xBefore = mario.getX();
            mario.stopAtLeftBoundary(50);
            assertEquals(50, mario.getX(), "stopAtLeftBoundary(50) phải đặt X = 50");
            assertEquals(0, mario.getVelX(), "velX phải = 0 sau khi dừng");
        });
    }

    @Test
    @DisplayName("Mario - stopAtLeftBoundary() không đặt X khi velX >= 0")
    void mario_stopAtLeftBoundaryIgnoredWhenVelXNonNegative() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            mario.setVelX(5);
            double xBefore = mario.getX();
            mario.stopAtLeftBoundary(50);
            assertEquals(xBefore, mario.getX(),
                "stopAtLeftBoundary không đặt X khi velX >= 0");
        });
    }

    @Test
    @DisplayName("Mario - onTouchEnemy với SMALL form post MARIO_DIES")
    void mario_smallFormOnTouchEnemyPostsMarioDies() {
        EventBus.getInstance().reset();
        boolean[] fired = {false};
        EventBus.getInstance().subscribe(GameEvent.MARIO_DIES, d -> fired[0] = true);

        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            boolean result = mario.onTouchEnemy();
            assertTrue(fired[0], "SMALL Mario onTouchEnemy phải post MARIO_DIES");
            assertTrue(result, "SMALL Mario onTouchEnemy phải trả về true");
        });
    }

    @Test
    @DisplayName("Mario - fire() với FIRE form trả Fireball")
    void mario_fireFormShootsFireball() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            MarioForm fireForm = new MarioForm(null, false, true);
            mario.setMarioForm(fireForm);
            Fireball fb = mario.fire();
            assertNotNull(fb, "FIRE Mario.fire() phải trả Fireball không null");
        });
    }

    @Test
    @DisplayName("Mario - fire() với SMALL form trả null")
    void mario_smallFormFireReturnsNull() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            Fireball fb = mario.fire();
            assertNull(fb, "SMALL Mario.fire() phải trả null");
        });
    }

    @Test
    @DisplayName("Mario - fire() với SUPER form trả null")
    void mario_superFormFireReturnsNull() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            MarioForm superForm = new MarioForm(null, true, false);
            mario.setMarioForm(superForm);
            Fireball fb = mario.fire();
            assertNull(fb, "SUPER Mario.fire() phải trả null");
        });
    }

    @Test
    @DisplayName("Mario - getPoints và getCoins trả giá trị đúng")
    void mario_getPointsAndCoinsReturnCorrectValues() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertEquals(0, mario.getPoints(), "Points ban đầu phải = 0");
            assertEquals(0, mario.getCoins(), "Coins ban đầu phải = 0");

            mario.acquirePoints(250);
            mario.acquireCoin();
            mario.acquireCoin();
            assertEquals(250, mario.getPoints(), "Points phải = 250");
            assertEquals(2, mario.getCoins(), "Coins phải = 2");
        });
    }

    // ============================================================
    // 2. Enemy Tests
    // ============================================================

    @Test
    @DisplayName("Enemy - khởi tạo với tọa độ đúng")
    void enemy_initAtCorrectPosition() {
        Enemy enemy = new Enemy(200, 300, null);
        assertEquals(200, enemy.getX(), "Enemy X phải bằng 200");
        assertEquals(300, enemy.getY(), "Enemy Y phải bằng 300");
    }

    @Test
    @DisplayName("Enemy - reverseDirection() đảo velX")
    void enemy_reverseDirectionInvertsVelX() {
        Enemy enemy = new Enemy(200, 300, null);
        double velBefore = enemy.getVelX();
        enemy.reverseDirection();
        assertEquals(-velBefore, enemy.getVelX(),
            "reverseDirection() phải đảo dấu velX");
    }

    @Test
    @DisplayName("Enemy - reverseDirection 2 lần → velX về ban đầu (0)")
    void enemy_doubleReverseBackToOriginal() {
        Enemy enemy = new Enemy(200, 300, null);
        double original = enemy.getVelX();
        enemy.reverseDirection();
        enemy.reverseDirection();
        assertEquals(original, enemy.getVelX(),
            "Đảo 2 lần phải trả velX về giá trị ban đầu (0)");
    }

    @Test
    @DisplayName("Enemy - reverseDirection 2 lần với velX ban đầu = 10")
    void enemy_doubleReverseWithInitialVelX() {
        Enemy enemy = new Enemy(200, 300, null);
        enemy.setVelX(10.0);
        double original = 10.0;
        enemy.reverseDirection();
        assertEquals(-10.0, enemy.getVelX());
        enemy.reverseDirection();
        assertEquals(original, enemy.getVelX(),
            "Đảo 2 lần với velX=10 phải về 10");
    }

    @Test
    @DisplayName("Enemy - landOnSurface đặt đúng vị trí")
    void enemy_landOnSurfacePositionsCorrectly() {
        Enemy enemy = new Enemy(200, 250, null);
        enemy.setDimension(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
        enemy.landOnSurface(300);
        int expectedY = 300 - GameConstants.BRICK_SIZE;
        assertEquals(expectedY, enemy.getY(),
            "landOnSurface(300) phải đặt Enemy trên surface y=300");
    }

    @Test
    @DisplayName("Enemy - falling ban đầu là false")
    void enemy_initialFallingState() {
        Enemy enemy = new Enemy(200, 300, null);
        assertFalse(enemy.isFalling(),
            "Enemy mới tạo phải không falling");
    }

    // ============================================================
    // 3. Turtle Tests
    // ============================================================

    @Test
    @DisplayName("Turtle - constructor đặt velX = ENEMY_MOVE_SPEED")
    void turtle_constructorSetsVelX() {
        Turtle turtle = new Turtle(200, 300, null);
        assertEquals(GameConstants.ENEMY_MOVE_SPEED, turtle.getVelX(),
            "Turtle velX phải bằng ENEMY_MOVE_SPEED");
    }

    @Test
    @DisplayName("Turtle - kế thừa reverseDirection()")
    void turtle_inheritsReverseDirection() {
        Turtle turtle = new Turtle(200, 300, null);
        double velBefore = turtle.getVelX();
        turtle.reverseDirection();
        assertEquals(-velBefore, turtle.getVelX(),
            "Turtle phải đảo chiều được");
    }

    @Test
    @DisplayName("Turtle - reverseDirection 2 lần về giá trị ban đầu")
    void turtle_doubleReverseBackToOriginal() {
        Turtle turtle = new Turtle(200, 300, null);
        double original = turtle.getVelX();
        turtle.reverseDirection();
        turtle.reverseDirection();
        assertEquals(original, turtle.getVelX(),
            "Đảo 2 lần phải về ENEMY_MOVE_SPEED");
    }

    // ============================================================
    // 4. EndFlag Tests
    // ============================================================

    @Test
    @DisplayName("EndFlag - khởi tạo với vị trí đúng")
    void endFlag_initAtPosition() {
        EndFlag flag = new EndFlag(1900, 250, null);
        assertEquals(1900, flag.getX(),
            "EndFlag X phải bằng 1900");
        assertEquals(250, flag.getY(),
            "EndFlag Y phải bằng 250");
    }

    @Test
    @DisplayName("EndFlag - isTouched() ban đầu false")
    void endFlag_notTouchedInitially() {
        EndFlag flag = new EndFlag(1900, 250, null);
        assertFalse(flag.isTouched(),
            "EndFlag isTouched() ban đầu phải là false");
    }

    @Test
    @DisplayName("EndFlag - setTouched(true) thay đổi trạng thái")
    void endFlag_setTouchedChangesState() {
        EndFlag flag = new EndFlag(1900, 250, null);
        assertFalse(flag.isTouched());
        flag.setTouched(true);
        assertTrue(flag.isTouched(),
            "setTouched(true) phải thay đổi isTouched()");
    }

    @Test
    @DisplayName("EndFlag - setTouched(false) đặt lại trạng thái")
    void endFlag_setTouchedFalseResets() {
        EndFlag flag = new EndFlag(1900, 250, null);
        flag.setTouched(true);
        flag.setTouched(false);
        assertFalse(flag.isTouched(),
            "setTouched(false) phải đặt lại isTouched()");
    }

    // ============================================================
    // 5. Fireball Tests
    // ============================================================

    @Test
    @DisplayName("Fireball - constructor với toRight=true đặt velX dương")
    void fireball_toRightSetsPositiveVelX() {
        BufferedImage style = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Fireball fb = new Fireball(100, 300, style, true);
        assertEquals(GameConstants.FIREBALL_SPEED, fb.getVelX(),
            "Fireball đi phải có velX = FIREBALL_SPEED");
    }

    @Test
    @DisplayName("Fireball - constructor với toRight=false đặt velX âm")
    void fireball_toLeftSetsNegativeVelX() {
        BufferedImage style = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Fireball fb = new Fireball(100, 300, style, false);
        assertEquals(-GameConstants.FIREBALL_SPEED, fb.getVelX(),
            "Fireball đi trái phải có velX = -FIREBALL_SPEED");
    }

    @Test
    @DisplayName("Fireball - getBounds không null")
    void fireball_getBoundsNotNull() {
        BufferedImage style = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Fireball fb = new Fireball(100, 300, style, true);
        assertNotNull(fb.getBounds(), "getBounds() không được null");
    }

    // ============================================================
    // 6. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("Scenario - Mario stomps enemy từ trên")
    void scenario_marioStompsEnemy() {
        EventBus.getInstance().reset();
        boolean[] fired = {false};
        EventBus.getInstance().subscribe(GameEvent.STOMP_ENEMY, d -> fired[0] = true);

        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 248);
            mario.setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);
            mario.setVelY(2);
            mario.setFalling(true);
            map.setMario(mario);

            Enemy enemy = new Enemy(100, 276, null);
            enemy.setDimension(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
            map.addEnemy(enemy);

            GroundBrick ground = new GroundBrick(0, 380, null);
            map.addGroundBrick(ground);

            int pointsBefore = mario.getPoints();
            cs.checkAll(map, null);

            assertTrue(fired[0],
                "STOMP_ENEMY event phải được post khi Mario đạp Enemy");
            assertTrue(mario.getPoints() >= pointsBefore + GameConstants.STOMP_POINTS,
                "Mario phải acquire STOMP_POINTS");
        });
    }

    @Test
    @DisplayName("Scenario - Mario side collision với enemy")
    void scenario_marioSideCollisionWithEnemy() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 290);
            mario.setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);
            Camera cam = new Camera();
            mario.move(true, cam);
            map.setMario(mario);

            Enemy enemy = new Enemy(140, 300, null);
            enemy.setDimension(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
            map.addEnemy(enemy);

            GroundBrick ground = new GroundBrick(0, 380, null);
            map.addGroundBrick(ground);

            cs.checkAll(map, null);
        }, "checkAll không crash khi chạm Enemy");
    }

    @Test
    @DisplayName("Scenario - Mario SMALL onTouchEnemy gây reset map")
    void scenario_smallMarioOnTouchEnemyCallsReset() {
        EventBus.getInstance().reset();

        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 290);
            boolean result = mario.onTouchEnemy();
            assertTrue(result,
                "SMALL Mario onTouchEnemy phải trả về true (để reset map)");
        });
    }

    // ============================================================
    // 7. Partition Testing
    // ============================================================

    @Test
    @DisplayName("Partition - Mario SMALL form (isSuper=false, isFire=false)")
    void partition_marioSmallForm() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertFalse(mario.getMarioForm().isSuper(),
                "Mario khởi tạo phải là SMALL form (isSuper=false)");
            assertFalse(mario.getMarioForm().isFire(),
                "Mario khởi tạo phải là SMALL form (isFire=false)");
            assertFalse(mario.isSuper(),
                "Mario.isSuper() phải trả false");
        });
    }

    @Test
    @DisplayName("Partition - Mario ở vị trí biên (y rất lớn)")
    void partition_marioAtHighY() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 10000);
            assertEquals(10000, mario.getY(),
                "Mario có thể ở y = 10000");
        });
    }

    @Test
    @DisplayName("Partition - Mario ở vị trí biên (x rất nhỏ)")
    void partition_marioAtLowX() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(-1000, 300);
            assertEquals(-1000, mario.getX(),
                "Mario có thể ở x = -1000");
        });
    }

    // ============================================================
    // 8. Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("Guideline - Enemy với tọa độ âm không crash")
    void guideline_enemyNegativeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new Enemy(-100, -50, null),
            "Enemy(-100,-50) không crash");
    }

    @Test
    @DisplayName("Guideline - EndFlag với tọa độ 0 không crash")
    void guideline_endFlagAtOriginDoesNotCrash() {
        assertDoesNotThrow(() -> new EndFlag(0, 0, null),
            "EndFlag(0,0) không crash");
    }

    @Test
    @DisplayName("Guideline - CollisionSystem.checkFireballContact() với map rỗng")
    void guideline_checkFireballContactEmptyMap() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 300);
            map.setMario(mario);
            cs.checkAll(map, null);
        }, "checkAll với map không có fireball không crash");
    }

    @Test
    @DisplayName("Guideline - Turtle với tọa độ âm không crash")
    void guideline_turtleNegativeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new Turtle(-200, -100, null),
            "Turtle(-200,-100) không crash");
    }

    @Test
    @DisplayName("Guideline - Fireball với tọa độ lớn không crash")
    void guideline_fireballLargeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> {
            BufferedImage style = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            new Fireball(100000, 100000, style, true);
        }, "Fireball với tọa độ lớn không crash");
    }

    @Test
    @DisplayName("Guideline - Mario jump khi đã jumping không thay đổi")
    void guideline_jumpWhileJumpingIgnored() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            mario.setJumping(true);
            mario.setFalling(false);
            double velYBefore = mario.getVelY();
            mario.jump();
            assertEquals(velYBefore, mario.getVelY(),
                "jump() khi đang jumping không thay đổi velY");
        });
    }
}
