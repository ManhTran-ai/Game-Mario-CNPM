package test.nguoi2;

import controller.CollisionSystem;
import manager.Camera;
import manager.GameConstants;
import model.Map;
import model.brick.GroundBrick;
import model.brick.OrdinaryBrick;
import model.hero.Mario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Development Testing - Người 2 tự kiểm thử code của mình:
 * CollisionSystem, GameObject (via Mario), Camera
 *
 * Phạm vi:
 * - Unit test cho từng class/method
 * - Partition testing (biên, trung tâm)
 * - Guideline-based testing (null, edge)
 */
public class DevelopmentTest {

    // ============================================================
    // GameObject Tests (via Mario)
    // ============================================================

    @Test
    @DisplayName("GameObject - getX() và setX() hoạt động đúng")
    void gameObject_getSetX() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setX(150);
            assertEquals(150, mario.getX(), "setX(150) phải cập nhật getX() = 150");
        });
    }

    @Test
    @DisplayName("GameObject - getY() và setY() hoạt động đúng")
    void gameObject_getSetY() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setY(250);
            assertEquals(250, mario.getY(), "setY(250) phải cập nhật getY() = 250");
        });
    }

    @Test
    @DisplayName("GameObject - getVelX() và setVelX() hoạt động đúng")
    void gameObject_getSetVelX() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setVelX(5.0);
            assertEquals(5.0, mario.getVelX(), "setVelX(5.0) phải cập nhật getVelX() = 5.0");
        });
    }

    @Test
    @DisplayName("GameObject - getVelY() và setVelY() hoạt động đúng")
    void gameObject_getSetVelY() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setVelY(-10.0);
            assertEquals(-10.0, mario.getVelY(), "setVelY(-10.0) phải cập nhật getVelY() = -10.0");
        });
    }

    @Test
    @DisplayName("GameObject - getBounds() trả Rectangle không null")
    void gameObject_getBoundsNotNull() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getBounds(), "getBounds() không được null");
        });
    }

    @Test
    @DisplayName("GameObject - getBottomBounds() không null")
    void gameObject_getBottomBoundsNotNull() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getBottomBounds(), "getBottomBounds() không được null");
        });
    }

    @Test
    @DisplayName("GameObject - getTopBounds() không null")
    void gameObject_getTopBoundsNotNull() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getTopBounds(), "getTopBounds() không được null");
        });
    }

    @Test
    @DisplayName("GameObject - isFalling() ban đầu là true")
    void gameObject_initialFallingState() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertTrue(mario.isFalling(), "Mario mới tạo phải đang ở trạng thái falling");
        });
    }

    @Test
    @DisplayName("GameObject - setFalling(false) thay đổi trạng thái")
    void gameObject_setFallingChangesState() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setFalling(false);
            assertFalse(mario.isFalling(), "Sau setFalling(false) thì isFalling() phải false");
        });
    }

    @Test
    @DisplayName("GameObject - landOnSurface() đặt đúng vị trí")
    void gameObject_landOnSurfacePositionsCorrectly() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);
            mario.landOnSurface(300);
            int expectedY = 300 - GameConstants.SMALL_MARIO_HEIGHT;
            assertEquals(expectedY, mario.getY(), "landOnSurface(300) phải đặt Y = 300 - height");
        });
    }

    @Test
    @DisplayName("GameObject - hitCeiling() thay đổi vị trí Y")
    void gameObject_hitCeilingChangesY() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);
            double yBefore = mario.getY();
            mario.hitCeiling(100);
            assertNotEquals(yBefore, mario.getY(), "hitCeiling(100) phải thay đổi vị trí Y");
        });
    }

    // ============================================================
    // Camera Tests
    // ============================================================

    @Test
    @DisplayName("Camera - khởi tạo với x=0, y=0")
    void camera_initialPositionAtZero() {
        Camera cam = new Camera();
        assertEquals(0, cam.getX(), "Camera X ban đầu phải bằng 0");
        assertEquals(0, cam.getY(), "Camera Y ban đầu phải bằng 0");
    }

    @Test
    @DisplayName("Camera - setX() và getX()")
    void camera_setXAndGetX() {
        Camera cam = new Camera();
        cam.setX(300);
        assertEquals(300, cam.getX(), "setX(300) phải cập nhật getX() = 300");
    }

    @Test
    @DisplayName("Camera - setY() và getY()")
    void camera_setYAndGetY() {
        Camera cam = new Camera();
        cam.setY(100);
        assertEquals(100, cam.getY(), "setY(100) phải cập nhật getY() = 100");
    }

    @Test
    @DisplayName("Camera - moveCam() dịch chuyển theo xAmount")
    void camera_moveCamShiftsByXAmount() {
        Camera cam = new Camera();
        cam.setX(0);
        cam.moveCam(10, 0);
        assertTrue(cam.getX() > 0, "moveCam(10, 0) phải tăng X");
    }

    @Test
    @DisplayName("Camera - shakeCamera() cho phép gọi moveCam")
    void camera_shakeCameraAllowsMoveCam() {
        Camera cam = new Camera();
        cam.shakeCamera();
        assertDoesNotThrow(() -> cam.moveCam(0, 0),
            "shakeCamera() phải cho phép moveCam được gọi");
    }

    // ============================================================
    // CollisionSystem Tests
    // ============================================================

    @Test
    @DisplayName("CollisionSystem - constructor public, tạo được instance")
    void collisionSystem_canCreateInstance() {
        CollisionSystem cs = new CollisionSystem();
        assertNotNull(cs, "CollisionSystem phải tạo được instance");
    }

    @Test
    @DisplayName("CollisionSystem - checkAll() không crash với map rỗng")
    void collisionSystem_checkAllWithEmptyMap() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 300);
            map.setMario(mario);
            cs.checkAll(map, null);
        }, "checkAll với map rỗng không được crash");
    }

    @Test
    @DisplayName("CollisionSystem - checkAll() với null không crash")
    void collisionSystem_checkAllWithNull() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            cs.checkAll(null, null);
        }, "checkAll(null) không được crash");
    }

    @Test
    @DisplayName("CollisionSystem - checkAll() với map có Mario không crash")
    void collisionSystem_checkAllWithMarioDoesNotCrash() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 300);
            map.setMario(mario);
            GroundBrick ground = new GroundBrick(0, 380, null);
            map.addGroundBrick(ground);
            cs.checkAll(map, null);
        }, "checkAll với map có Mario không crash");
    }

    @Test
    @DisplayName("CollisionSystem - checkAll() với map có bricks không crash")
    void collisionSystem_checkAllWithBricksDoesNotCrash() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 240);
            map.setMario(mario);
            GroundBrick ground = new GroundBrick(0, 380, null);
            map.addGroundBrick(ground);
            OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
            map.addBrick(brick);
            cs.checkAll(map, null);
        }, "checkAll với map có bricks không crash");
    }

    @Test
    @DisplayName("CollisionSystem - checkFireballContact() với map rỗng gọi được")
    void collisionSystem_checkFireballContactCallable() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 300);
            map.setMario(mario);
            cs.checkAll(map, null);
        }, "checkAll với map rỗng không crash (chứa checkFireballContact)");
    }

    @Test
    @DisplayName("CollisionSystem - checkFireballContact() với fireballs không crash")
    void collisionSystem_checkFireballContactWithFireballs() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 300);
            map.setMario(mario);
            BufferedImage style = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            model.hero.Fireball fb = new model.hero.Fireball(200, 300, style, true);
            map.addFireball(fb);
            cs.checkAll(map, null);
        }, "checkAll với fireball không crash");
    }

    @Test
    @DisplayName("CollisionSystem - checkAll() với enemies không crash")
    void collisionSystem_checkAllWithEnemiesDoesNotCrash() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            Mario mario = new Mario(100, 300);
            map.setMario(mario);
            GroundBrick ground = new GroundBrick(0, 380, null);
            map.addGroundBrick(ground);
            model.Enemy.Enemy enemy = new model.Enemy.Enemy(200, 300, null);
            enemy.setDimension(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
            map.addEnemy(enemy);
            cs.checkAll(map, null);
        }, "checkAll với map có enemies không crash");
    }

    // ============================================================
    // Mario Movement Tests
    // ============================================================

    @Test
    @DisplayName("Mario - move(true, camera) đặt velX > 0")
    void mario_moveRightSetsVelXPositive() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            Camera cam = new Camera();
            mario.move(true, cam);
            assertEquals(GameConstants.MARIO_MOVE_SPEED, mario.getVelX(),
                "move(true) phải đặt velX = MARIO_MOVE_SPEED");
        });
    }

    @Test
    @DisplayName("Mario - move(false, camera) khi x > camera.x đặt velX < 0")
    void mario_moveLeftWhenAheadOfCameraSetsVelXNegative() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(500, 300);
            Camera cam = new Camera();
            cam.setX(100);
            mario.move(false, cam);
            assertEquals(-GameConstants.MARIO_MOVE_SPEED, mario.getVelX(),
                "move(false) khi x > camera.x phải đặt velX = -MARIO_MOVE_SPEED");
        });
    }

    @Test
    @DisplayName("Mario - move(false, camera) khi x <= camera.x giữ velX = 0")
    void mario_moveLeftWhenBehindCameraKeepsVelXZero() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            Camera cam = new Camera();
            cam.setX(500);
            double velBefore = mario.getVelX();
            mario.move(false, cam);
            assertEquals(velBefore, mario.getVelX(),
                "move(false) khi x <= camera.x không thay đổi velX");
        });
    }

    @Test
    @DisplayName("Mario - jump() chỉ hoạt động khi !jumping && velY <= 0")
    void mario_jumpOnlyWhenGrounded() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 300);
            assertTrue(mario.isFalling(), "Mario mới tạo đang falling");
            assertFalse(mario.isJumping(), "Mario mới tạo không jumping");
            mario.jump();
            assertTrue(mario.isJumping() || mario.getVelY() != 0,
                "jump() phải thay đổi trạng thái khi grounded");
        });
    }

    // ============================================================
    // Guideline-based
    // ============================================================

    @Test
    @DisplayName("Guideline - Mario với x=0, y=0 không crash")
    void guideline_marioAtOriginDoesNotCrash() {
        assertDoesNotThrow(() -> new Mario(0, 0),
            "Mario(0,0) không được crash");
    }

    @Test
    @DisplayName("Guideline - CollisionSystem.checkAll(null, null) không crash")
    void guideline_checkAllWithNullMapDoesNotCrash() {
        CollisionSystem cs = new CollisionSystem();
        assertDoesNotThrow(() -> cs.checkAll(null, null),
            "checkAll(null, null) không crash");
    }

    @Test
    @DisplayName("Guideline - Camera với giá trị cực lớn không crash")
    void guideline_cameraWithLargeValuesDoesNotCrash() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> {
            cam.setX(Integer.MAX_VALUE);
            cam.setY(Integer.MAX_VALUE);
        }, "Camera với giá trị cực lớn không crash");
    }

    @Test
    @DisplayName("Guideline - Camera với giá trị âm cực nhỏ không crash")
    void guideline_cameraWithMinValuesDoesNotCrash() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> {
            cam.setX(Integer.MIN_VALUE);
            cam.setY(Integer.MIN_VALUE);
        }, "Camera với giá trị MIN_VALUE không crash");
    }

    @Test
    @DisplayName("Guideline - CollisionSystem với map không có Mario")
    void guideline_checkAllWithMapNoMario() {
        assertDoesNotThrow(() -> {
            CollisionSystem cs = new CollisionSystem();
            Map map = new Map(300, null);
            GroundBrick ground = new GroundBrick(0, 380, null);
            map.addGroundBrick(ground);
            cs.checkAll(map, null);
        }, "checkAll với map không có Mario không crash");
    }

    @Test
    @DisplayName("Guideline - GroundBrick với tọa độ âm không crash")
    void guideline_groundBrickNegativeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new GroundBrick(-100, -50, null),
            "GroundBrick(-100,-50) không crash");
    }

    @Test
    @DisplayName("Guideline - Map với remainingTime = 0 không crash")
    void guideline_mapWithZeroTimeDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(0, null),
            "Map(0, null) không crash");
    }
}
