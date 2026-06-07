package test.test.nguoi3;

import manager.Camera;
import manager.GameConstants;
import model.EndFlag;
import model.Enemy.Enemy;
import model.Enemy.Turtle;
import model.hero.Fireball;
import model.hero.Mario;
import model.hero.MarioForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Nguoi3_DevelopmentTest {

    // ============================================================
    // Mario Tests
    // ============================================================

    @Test
    @DisplayName("Mario - khoi tao vi tri dung")
    void mario_initAtCorrectPosition() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        assertEquals(100, mario.getX(), "X phai bang 100");
        assertEquals(300, mario.getY(), "Y phai bang 300");
    }

    @Test
    @DisplayName("Mario - form mac dinh la SMALL (isSuper=false, isFire=false)")
    void mario_defaultFormIsSmall() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        assertFalse(mario.getMarioForm().isSuper(),
            "Mario khoi tao phai khong phai SUPER");
        assertFalse(mario.getMarioForm().isFire(),
            "Mario khoi tao phai khong phai FIRE");
    }

    @Test
    @DisplayName("Mario - di chuyen phai dat velX = 5.0")
    void mario_moveRightSetsVelX() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        Camera cam = new Camera();
        mario.move(true, cam);
        assertEquals(5.0, mario.getVelX(),
            "moveRight phai dat velX = 5.0");
    }

    @Test
    @DisplayName("Mario - di chuyen trai dat velX = -5.0 khi x > camera.x")
    void mario_moveLeftSetsVelX() {
        Mario mario = assertDoesNotThrow(() -> new Mario(500, 300));
        Camera cam = new Camera();
        cam.setX(100);
        mario.move(false, cam);
        assertEquals(-5.0, mario.getVelX(),
            "moveLeft khi x > camera.x phai dat velX = -5.0");
    }

    @Test
    @DisplayName("Mario - jump() dat velY=MARIO_JUMP_VELOCITY=10.0 va isJumping=true (setFalling false truoc)")
    void mario_jumpSetsJumpingState() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        mario.setFalling(false);
        int velYBefore = (int) mario.getVelY();
        mario.jump();
        assertTrue(mario.isJumping(),
            "jump() phai dat isJumping=true");
        assertTrue(mario.getVelY() < velYBefore || mario.getVelY() == GameConstants.MARIO_JUMP_VELOCITY,
            "jump() phai thay doi velY");
    }

    @Test
    @DisplayName("Mario - acquirePoints tang diem")
    void mario_acquirePointsIncreasesScore() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        int before = mario.getPoints();
        mario.acquirePoints(100);
        assertTrue(mario.getPoints() > before,
            "acquirePoints(100) phai tang diem");
    }

    @Test
    @DisplayName("Mario - acquireCoin tang coin count")
    void mario_acquireCoinIncrementsCount() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        int before = mario.getCoins();
        mario.acquireCoin();
        assertEquals(before + 1, mario.getCoins(),
            "acquireCoin() phai tang coin count them 1");
    }

    @Test
    @DisplayName("Mario - setMarioForm thay doi form")
    void mario_setMarioFormChangesForm() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        MarioForm superForm = new MarioForm(null, true, false);
        mario.setMarioForm(superForm);
        assertTrue(mario.getMarioForm().isSuper(),
            "setMarioForm(SUPER) phai thay doi form");
    }

    @Test
    @DisplayName("Mario - isSuper() tra ve dung")
    void mario_isSuperReturnsCorrect() {
        Mario mario = assertDoesNotThrow(() -> new Mario(100, 300));
        assertFalse(mario.isSuper(),
            "Mario moi tao isSuper() phai false");
    }

    @Test
    @DisplayName("Mario - resetLocation() dat X ve 50")
    void mario_resetLocationSetsX() {
        Mario mario = assertDoesNotThrow(() -> new Mario(500, 200));
        mario.resetLocation();
        assertEquals(50, mario.getX(),
            "resetLocation() phai dat X = 50");
    }

    @Test
    @DisplayName("Mario - stopAtX() dat X va velX=0")
    void mario_stopAtXChangesPositionAndVelX() {
        Mario mario = assertDoesNotThrow(() -> new Mario(200, 300));
        mario.setVelX(5);
        mario.stopAtX(150);
        assertEquals(150, mario.getX(),
            "stopAtX(150) phai dat X = 150");
        assertEquals(0, mario.getVelX(),
            "stopAtX() phai dat velX = 0");
    }

    // ============================================================
    // MarioForm Tests
    // ============================================================

    @Test
    @DisplayName("MarioForm - SMALL=0, SUPER=1, FIRE=2")
    void marioForm_enumValues() {
        assertEquals(0, MarioForm.SMALL, "SMALL phai bang 0");
        assertEquals(1, MarioForm.SUPER, "SUPER phai bang 1");
        assertEquals(2, MarioForm.FIRE, "FIRE phai bang 2");
    }

    @Test
    @DisplayName("MarioForm - constructor voi isSuper=true, isFire=false")
    void marioForm_superConstructor() {
        MarioForm form = new MarioForm(null, true, false);
        assertTrue(form.isSuper(),
            "MarioForm(true,false) phai isSuper=true");
        assertFalse(form.isFire(),
            "MarioForm(true,false) phai isFire=false");
    }

    @Test
    @DisplayName("MarioForm - constructor voi isSuper=false, isFire=true")
    void marioForm_fireConstructor() {
        MarioForm form = new MarioForm(null, false, true);
        assertFalse(form.isSuper(),
            "MarioForm(false,true) phai isSuper=false");
        assertTrue(form.isFire(),
            "MarioForm(false,true) phai isFire=true");
    }

    @Test
    @DisplayName("MarioForm - setSuper thay doi trang thai")
    void marioForm_setSuper() {
        MarioForm form = new MarioForm(null, false, false);
        form.setSuper(true);
        assertTrue(form.isSuper(),
            "setSuper(true) phai thay doi isSuper thanh true");
    }

    // ============================================================
    // Fireball Tests
    // ============================================================

    @Test
    @DisplayName("Fireball - constructor dat velX=FIREBALL_SPEED(10.0) cho huong phai")
    void fireball_rightConstructorSetsVelX() {
        Fireball fb = new Fireball(100, 240, null, true);
        assertEquals(GameConstants.FIREBALL_SPEED, fb.getVelX(),
            "Fireball(true) phai co velX = FIREBALL_SPEED");
    }

    @Test
    @DisplayName("Fireball - constructor dat velX=-FIREBALL_SPEED(-10.0) cho huong trai")
    void fireball_leftConstructorSetsNegativeVelX() {
        Fireball fb = new Fireball(100, 240, null, false);
        assertEquals(-GameConstants.FIREBALL_SPEED, fb.getVelX(),
            "Fireball(false) phai co velX = -FIREBALL_SPEED");
    }

    // ============================================================
    // Enemy Tests
    // ============================================================

    @Test
    @DisplayName("Enemy - khoi tao vi tri dung")
    void enemy_initAtCorrectPosition() {
        Enemy enemy = new Enemy(200, 300, null);
        assertEquals(200, enemy.getX());
        assertEquals(300, enemy.getY());
    }

    @Test
    @DisplayName("Enemy - reverseDirection() dao chieu velX")
    void enemy_reverseDirection() {
        Enemy enemy = new Enemy(200, 300, null);
        double velBefore = enemy.getVelX();
        enemy.reverseDirection();
        assertEquals(-velBefore, enemy.getVelX(),
            "reverseDirection() phai dao dau velX");
    }

    // ============================================================
    // Turtle Tests
    // ============================================================

    @Test
    @DisplayName("Turtle - ke thua Enemy, constructor dat velX=10.0")
    void turtle_constructorSetsVelX() {
        Turtle turtle = new Turtle(200, 300, null);
        assertEquals(10.0, turtle.getVelX(),
            "Turtle constructor phai dat velX = 10.0");
    }

    @Test
    @DisplayName("Turtle - reverseDirection() dao chieu velX")
    void turtle_reverseDirection() {
        Turtle turtle = new Turtle(200, 300, null);
        double velBefore = turtle.getVelX();
        turtle.reverseDirection();
        assertEquals(-velBefore, turtle.getVelX(),
            "Turtle reverseDirection() phai dao dau velX");
    }

    // ============================================================
    // EndFlag Tests
    // ============================================================

    @Test
    @DisplayName("EndFlag - khoi tao vi tri dung")
    void endFlag_initAtCorrectPosition() {
        EndFlag flag = new EndFlag(1900, 250, null);
        assertEquals(1900, flag.getX());
        assertEquals(250, flag.getY());
    }

    @Test
    @DisplayName("EndFlag - isTouched ban dau false")
    void endFlag_touchedInitiallyFalse() {
        EndFlag flag = new EndFlag(1900, 250, null);
        assertFalse(flag.isTouched(),
            "EndFlag isTouched ban dau phai la false");
    }

    @Test
    @DisplayName("EndFlag - setTouched(true) thay doi trang thai")
    void endFlag_setTouchedChangesState() {
        EndFlag flag = new EndFlag(1900, 250, null);
        flag.setTouched(true);
        assertTrue(flag.isTouched(),
            "setTouched(true) phai thay doi isTouched()");
    }

    // ============================================================
    // Guideline-based
    // ============================================================

    @Test
    @DisplayName("Guideline - Mario(0,0) khong crash")
    void guideline_marioAtOriginDoesNotCrash() {
        assertDoesNotThrow(() -> new Mario(0, 0),
            "Mario(0,0) khong duoc crash");
    }

    @Test
    @DisplayName("Guideline - Enemy voi toa do am khong crash")
    void guideline_enemyNegativeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new Enemy(-100, -50, null),
            "Enemy(-100,-50) khong duoc crash");
    }

    @Test
    @DisplayName("Guideline - Fireball voi huong bat ky khong crash")
    void guideline_fireballAnyDirectionDoesNotCrash() {
        assertDoesNotThrow(() -> new Fireball(100, 240, null, true),
            "Fireball(true) khong crash");
        assertDoesNotThrow(() -> new Fireball(100, 240, null, false),
            "Fireball(false) khong crash");
    }

    @Test
    @DisplayName("Guideline - EndFlag tai goc toa do khong crash")
    void guideline_endFlagAtZeroDoesNotCrash() {
        assertDoesNotThrow(() -> new EndFlag(0, 0, null),
            "EndFlag(0,0) khong crash");
    }
}
