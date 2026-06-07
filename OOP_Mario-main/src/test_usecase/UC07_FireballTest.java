package test_usecase;

import controller.CollisionSystem;
import event.EventBus;
import event.GameEvent;
import manager.GameConstants;
import model.Map;
import model.brick.GroundBrick;
import model.hero.Fireball;
import model.hero.Mario;
import model.hero.MarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Case cho UC07 — Mario FIRE Bắn Fireball
 * ─────────────────────────────────────────────────────
 * Mục tiêu : Kiểm thử Mario ở dạng FIRE bắn Fireball,
 *             Fireball di chuyển ngang, phát nổ khi chạm Brick/Enemy.
 *
 * Các test case :
 *   TC-UC07-001 : Mario FIRE.fire() trả Fireball không null
 *   TC-UC07-002 : Mario SMALL.fire() trả null
 *   TC-UC07-003 : Fireball(true) có velX = FIREBALL_SPEED
 *   TC-UC07-004 : Fireball(false) có velX = -FIREBALL_SPEED
 *   TC-UC07-005 : Map.addFireball() thêm thành công
 *   TC-UC07-006 : Map.getFireballs() trả danh sách fireball
 *   TC-UC07-007 : Fireball.updateLocation() thay đổi X
 *   TC-UC07-008 : EventBus post FIREBALL_FIRED được nhận
 *   TC-UC07-009 : CollisionSystem.checkFireballContact() gọi được
 *   TC-UC07-010 : MarioForm.fire() với FIRE form trả Fireball
 *
 * Tham chiếu : UC07_Sequence.puml
 */
public class UC07_FireballTest {

    private CollisionSystem collisionSystem;
    private Map map;
    private Mario fireMario;
    private boolean fireballFired;

    @BeforeEach
    void setUp() {
        EventBus.getInstance().reset();
        collisionSystem = new CollisionSystem();
        map = new Map(300, null);
        assertDoesNotThrow(() -> fireMario = new Mario(100, 300));
        fireMario.setMarioForm(new MarioForm(null, false, true));
        map.setMario(fireMario);
        map.addGroundBrick(new GroundBrick(0, 380, null));
        fireballFired = false;
        EventBus.getInstance().subscribe(GameEvent.FIREBALL_FIRED, d -> fireballFired = true);
    }

    // TC-UC07-001
    @Test
    @DisplayName("TC-UC07-001: Mario FIRE.fire() trả Fireball không null")
    void tcUc07001_fireMarioShootsFireball() {
        Fireball fb = fireMario.fire();
        assertNotNull(fb,
            "TC-UC07-001: FIRE Mario.fire() phải trả Fireball. Actual: " + fb);
    }

    // TC-UC07-002
    @Test
    @DisplayName("TC-UC07-002: Mario SMALL.fire() trả null")
    void tcUc07002_smallMarioCannotFire() {
        Mario smallMario = assertDoesNotThrow(() -> new Mario(100, 300));
        assertFalse(smallMario.getMarioForm().isFire(),
            "Setup: SMALL Mario không phải FIRE");
        Fireball fb = smallMario.fire();
        assertNull(fb,
            "TC-UC07-002: SMALL Mario.fire() phải trả null");
    }

    // TC-UC07-003
    @Test
    @DisplayName("TC-UC07-003: Fireball(true) có velX = FIREBALL_SPEED")
    void tcUc07003_fireballRightVelX() {
        Fireball fb = new Fireball(100, 240, null, true);
        assertEquals(GameConstants.FIREBALL_SPEED, fb.getVelX(),
            "TC-UC07-003: velX phải bằng FIREBALL_SPEED = " + GameConstants.FIREBALL_SPEED);
    }

    // TC-UC07-004
    @Test
    @DisplayName("TC-UC07-004: Fireball(false) có velX = -FIREBALL_SPEED")
    void tcUc07004_fireballLeftVelX() {
        Fireball fb = new Fireball(100, 240, null, false);
        assertEquals(-GameConstants.FIREBALL_SPEED, fb.getVelX(),
            "TC-UC07-004: velX phải bằng -FIREBALL_SPEED = " + (-GameConstants.FIREBALL_SPEED));
    }

    // TC-UC07-005
    @Test
    @DisplayName("TC-UC07-005: Map.addFireball() thêm fireball thành công")
    void tcUc07005_addFireball() {
        Fireball fb = new Fireball(100, 240, null, true);
        map.addFireball(fb);
        assertEquals(1, map.getFireballs().size(),
            "TC-UC07-005: fireballs phải có 1 fireball sau addFireball");
    }

    // TC-UC07-006
    @Test
    @DisplayName("TC-UC07-006: Map.getFireballs() trả danh sách fireball")
    void tcUc07006_getFireballs() {
        assertNotNull(map.getFireballs(),
            "TC-UC07-006: getFireballs() không được null");
    }

    // TC-UC07-007
    @Test
    @DisplayName("TC-UC07-007: Fireball.updateLocation() thay đổi X")
    void tcUc07007_fireballUpdateLocation() {
        Fireball fb = new Fireball(100, 240, null, true);
        double xBefore = fb.getX();
        fb.updateLocation();
        assertTrue(fb.getX() > xBefore,
            "TC-UC07-007: X phải tăng sau updateLocation. Before: " + xBefore + " | After: " + fb.getX());
    }

    // TC-UC07-008
    @Test
    @DisplayName("TC-UC07-008: EventBus post FIREBALL_FIRED được nhận")
    void tcUc07008_fireballEventReceived() {
        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.FIREBALL_FIRED, d -> fired[0] = true);
        EventBus.getInstance().post(GameEvent.FIREBALL_FIRED);
        assertTrue(fired[0],
            "TC-UC07-008: FIREBALL_FIRED event phải được nhận");
    }

    // TC-UC07-009
    @Test
    @DisplayName("TC-UC07-009: checkAll() với fireball không crash")
    void tcUc07009_checkFireballContact() {
        Fireball fb = new Fireball(100, 240, null, true);
        map.addFireball(fb);

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "TC-UC07-009: checkAll() không được crash");
    }

    // TC-UC07-010
    @Test
    @DisplayName("TC-UC07-010: MarioForm.fire() với FIRE form trả Fireball")
    void tcUc07010_fireFormFire() {
        MarioForm fireForm = new MarioForm(null, false, true);
        assertTrue(fireForm.isFire(),
            "Setup: form phải là FIRE");
        Fireball fb = fireForm.fire(true, 100, 240);
        assertNotNull(fb,
            "TC-UC07-010: FIRE form.fire() phải trả Fireball");
    }
}
