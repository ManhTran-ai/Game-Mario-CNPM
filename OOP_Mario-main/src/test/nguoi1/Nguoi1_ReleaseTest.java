package test.test.nguoi1;

import controller.CollisionSystem;
import manager.Camera;
import model.Map;
import model.brick.GroundBrick;
import model.hero.Mario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Release Testing - Người 1 kiểm thử domain của Người 2:
 * CollisionSystem, Camera, GameObject
 *
 * Mục đích: Xác nhận hệ thống va chạm và camera hoạt động đúng.
 * Lưu ý: Camera có constructor public, CollisionSystem có constructor public,
 * GameObject là abstract nên test qua concrete subclass (Mario, GroundBrick).
 */
public class Nguoi1_ReleaseTest {

    private CollisionSystem collisionSystem;

    // ============================================================
    // 1. Requirements-based Testing: CollisionSystem
    // ============================================================

    @Test
    @DisplayName("REQ-COL-001: CollisionSystem.checkAll() gọi được với map hợp lệ")
    void reqCol001_checkAllWithValidMap() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 300)));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "REQ-COL-001: checkAll với map hợp lệ không được crash");
    }

    @Test
    @DisplayName("REQ-COL-002: CollisionSystem.checkAll() với null không crash")
    void reqCol002_checkAllWithNullMap() {
        collisionSystem = new CollisionSystem();
        assertDoesNotThrow(() -> collisionSystem.checkAll(null, null),
            "REQ-COL-002: checkAll(null) không được crash");
    }

    @Test
    @DisplayName("REQ-COL-003: checkMarioHorizontalCollision() gọi được")
    void reqCol003_checkMarioHorizontalCollisionCallable() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 300)));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "REQ-COL-003: checkAll gọi được (chứa checkMarioHorizontalCollision)");
    }

    @Test
    @DisplayName("REQ-COL-004: checkBottomCollisions() gọi được")
    void reqCol004_checkBottomCollisionsCallable() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 300)));
        map.addGroundBrick(new GroundBrick(0, 380, null));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "REQ-COL-004: checkAll gọi được (chứa checkBottomCollisions)");
    }

    @Test
    @DisplayName("REQ-COL-005: checkTopCollisions() gọi được")
    void reqCol005_checkTopCollisionsCallable() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 240)));
        map.addGroundBrick(new GroundBrick(100, 200, null));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "REQ-COL-005: checkAll gọi được (chứa checkTopCollisions)");
    }

    @Test
    @DisplayName("REQ-COL-006: checkPrizeContact() gọi được")
    void reqCol006_checkPrizeContactCallable() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 300)));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "REQ-COL-006: checkAll gọi được (chứa checkPrizeContact)");
    }

    @Test
    @DisplayName("REQ-COL-007: checkFireballContact() gọi được")
    void reqCol007_checkFireballContactCallable() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 300)));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "REQ-COL-007: checkAll gọi được (chứa checkFireballContact)");
    }

    // ============================================================
    // 2. Requirements-based Testing: Camera
    // ============================================================

    @Test
    @DisplayName("REQ-CAM-001: Camera khởi tạo với x = 0, y = 0")
    void reqCam001_cameraInitialPositionAtZero() {
        Camera cam = new Camera();
        assertEquals(0, cam.getX(), "REQ-CAM-001: Camera X ban đầu phải bằng 0");
        assertEquals(0, cam.getY(), "REQ-CAM-001: Camera Y ban đầu phải bằng 0");
    }

    @Test
    @DisplayName("REQ-CAM-002: Camera.setX() và getX() hoạt động đúng")
    void reqCam002_cameraSetAndGetX() {
        Camera cam = new Camera();
        cam.setX(500);
        assertEquals(500, cam.getX(), "REQ-CAM-002: Camera X phải bằng 500 sau setX(500)");
    }

    @Test
    @DisplayName("REQ-CAM-003: Camera.setY() và getY() hoạt động đúng")
    void reqCam003_cameraSetAndGetY() {
        Camera cam = new Camera();
        cam.setY(100);
        assertEquals(100, cam.getY(), "REQ-CAM-003: Camera Y phải bằng 100 sau setY(100)");
    }

    @Test
    @DisplayName("REQ-CAM-004: Camera.shakeCamera() kích hoạt camera shake")
    void reqCam004_shakeCameraActivates() {
        Camera cam = new Camera();
        // shakeCamera() sets shaking=true and frameNumber=60
        // moveCam(0,0) should change x when shaking && frameNumber > 0
        double xBefore = cam.getX();
        cam.shakeCamera();
        cam.moveCam(0, 0);
        // After shakeCamera() + one moveCam, x should change due to shake effect
        assertNotEquals(xBefore, cam.getX(),
            "REQ-CAM-004: shakeCamera() phải kích hoạt thay đổi x khi moveCam được gọi");
    }

    @Test
    @DisplayName("REQ-CAM-005: Camera.moveCam() dịch chuyển x")
    void reqCam005_moveCamShiftsX() {
        Camera cam = new Camera();
        double xBefore = cam.getX();
        cam.moveCam(10, 0);
        assertEquals(xBefore + 10, cam.getX(),
            "REQ-CAM-005: Camera phải dịch chuyển x thêm 10 khi moveCam(10,0) được gọi");
    }

    // ============================================================
    // 3. Requirements-based Testing: GameObject (via Mario)
    // ============================================================

    @Test
    @DisplayName("REQ-OBJ-001: GameObject có getX(), getY()")
    void reqObj001_gameObjectHasPositionAccessors() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertEquals(100, mario.getX(), "REQ-OBJ-001: getX() phải trả về 100");
            assertEquals(200, mario.getY(), "REQ-OBJ-001: getY() phải trả về 200");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-002: GameObject có getVelX(), setVelX()")
    void reqObj002_gameObjectHasVelXAccessors() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setVelX(5.0);
            assertEquals(5.0, mario.getVelX(), "REQ-OBJ-002: getVelX() phải trả về 5.0");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-003: GameObject có getBounds()")
    void reqObj003_gameObjectHasGetBounds() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getBounds(), "REQ-OBJ-003: getBounds() không được null");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-004: GameObject có getBottomBounds()")
    void reqObj004_gameObjectHasGetBottomBounds() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getBottomBounds(),
                "REQ-OBJ-004: getBottomBounds() không được null");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-005: GameObject có getTopBounds()")
    void reqObj005_gameObjectHasGetTopBounds() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getTopBounds(),
                "REQ-OBJ-005: getTopBounds() không được null");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-006: GameObject có isFalling() và setFalling()")
    void reqObj006_gameObjectFallingState() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setFalling(true);
            assertTrue(mario.isFalling(), "REQ-OBJ-006: isFalling() phải trả về true");
            mario.setFalling(false);
            assertFalse(mario.isFalling(), "REQ-OBJ-006: isFalling() phải trả về false");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-007: GameObject có landOnSurface()")
    void reqObj007_gameObjectLandOnSurface() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.landOnSurface(300);
            assertEquals(300 - mario.getDimension().height, mario.getY(),
                "REQ-OBJ-007: landOnSurface(300) phải đặt Mario y = surfaceY - height");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-008: GameObject có getVelY() và setVelY()")
    void reqObj008_gameObjectHasVelYAccessors() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            mario.setVelY(10.0);
            assertEquals(10.0, mario.getVelY(), "REQ-OBJ-008: getVelY() phải trả về 10.0");
        });
    }

    @Test
    @DisplayName("REQ-OBJ-009: GameObject có getDimension()")
    void reqObj009_gameObjectHasGetDimension() {
        assertDoesNotThrow(() -> {
            Mario mario = new Mario(100, 200);
            assertNotNull(mario.getDimension(), "REQ-OBJ-009: getDimension() không được null");
            assertEquals(38, mario.getDimension().height,
                "REQ-OBJ-009: SMALL_MARIO_HEIGHT phải bằng 38");
            assertEquals(48, mario.getDimension().width,
                "REQ-OBJ-009: SMALL_MARIO_WIDTH phải bằng 48");
        });
    }

    // ============================================================
    // 4. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("SCN-COL-001: Mario đứng trên GroundBrick — bottom collision")
    void scnCol001_marioStandsOnGroundBrick() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 340)));
        map.addGroundBrick(new GroundBrick(0, 380, null));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "SCN-COL-001: checkAll với Mario trên GroundBrick không crash");
    }

    @Test
    @DisplayName("SCN-COL-002: Map rỗng — checkAll không crash")
    void scnCol002_emptyMapCheckAllDoesNotCrash() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        map.setMario(new Mario(100, 300));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "SCN-COL-002: checkAll với map rỗng không crash");
    }

    @Test
    @DisplayName("SCN-COL-003: Camera theo Mario — moveCam được gọi nhiều lần")
    void scnCol003_cameraMoveCamMultipleTimes() {
        Camera cam = new Camera();
        for (int i = 0; i < 100; i++) {
            cam.moveCam(5, 0);
        }
        assertEquals(500, cam.getX(),
            "SCN-COL-003: Camera phải dịch chuyển 500 đơn vị sau 100 lần moveCam(5,0)");
    }

    @Test
    @DisplayName("SCN-COL-004: Camera shake kết thúc sau 60 frame")
    void scnCol004_cameraShakeEndsAfter60Frames() {
        Camera cam = new Camera();
        cam.shakeCamera();
        // After 60 moveCam calls, frameNumber goes to 0 and shake stops
        for (int i = 0; i < 60; i++) {
            cam.moveCam(0, 0);
        }
        // After 60 frames, shake should stop; next moveCam(0,0) does NOT change x
        double xAfterShake = cam.getX();
        cam.moveCam(0, 0);
        assertEquals(xAfterShake, cam.getX(),
            "SCN-COL-004: Sau 60 frame shake, moveCam(0,0) không thay đổi x");
    }

    // ============================================================
    // 5. Partition Testing
    // ============================================================

    @Test
    @DisplayName("PART-COL-001: GroundBrick với kích thước chuẩn BRICK_SIZE=48")
    void partCol001_brickWithStandardSize() {
        // GroundBrick(double x, double y, BufferedImage style) — 3 params
        GroundBrick brick = new GroundBrick(100, 200, null);
        assertNotNull(brick.getBounds(),
            "PART-COL-001: GroundBrick phải có getBounds()");
        assertEquals(48, brick.getDimension().width,
            "PART-COL-001: GroundBrick width phải bằng 48 (BRICK_SIZE)");
        assertEquals(48, brick.getDimension().height,
            "PART-COL-001: GroundBrick height phải bằng 48 (BRICK_SIZE)");
    }

    @Test
    @DisplayName("PART-COL-002: Mario với vị trí biên (x=0)")
    void partCol002_marioAtZeroPosition() {
        Mario mario = new Mario(0, 300);
        assertEquals(0, mario.getX(),
            "PART-COL-002: Mario có thể khởi tạo tại x=0");
    }

    @Test
    @DisplayName("PART-COL-003: Camera với tọa độ âm")
    void partCol003_cameraWithNegativeCoord() {
        Camera cam = new Camera();
        cam.setX(-100);
        assertEquals(-100, cam.getX(),
            "PART-COL-003: Camera có thể set X âm");
        cam.setY(-50);
        assertEquals(-50, cam.getY(),
            "PART-COL-003: Camera có thể set Y âm");
    }

    // ============================================================
    // 6. Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("GUIDE-COL-001: CollisionSystem với controller null không crash")
    void guideCol001_collisionWithNullController() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> map.setMario(new Mario(100, 300)));
        map.addGroundBrick(new GroundBrick(0, 380, null));

        assertDoesNotThrow(() -> collisionSystem.checkAll(map, null),
            "GUIDE-COL-001: checkAll với null controller không crash");
    }

    @Test
    @DisplayName("GUIDE-COL-002: Camera.moveCam với tham số 0 không crash")
    void guideCol002_cameraMoveCamWithZeroParams() {
        Camera cam = new Camera();
        assertDoesNotThrow(() -> cam.moveCam(0, 0),
            "GUIDE-COL-002: moveCam(0,0) không crash");
    }

    @Test
    @DisplayName("GUIDE-COL-003: Mario với velX = 0 không crash")
    void guideCol003_marioWithZeroVelX() {
        Mario mario = new Mario(100, 300);
        mario.setVelX(0);
        assertEquals(0, mario.getVelX(),
            "GUIDE-COL-003: Mario với velX=0 hoạt động bình thường");
    }

    @Test
    @DisplayName("GUIDE-COL-004: GroundBrick.isBreakable() và isEmpty()")
    void guideCol004_groundBrickStates() {
        GroundBrick brick = new GroundBrick(0, 380, null);
        assertFalse(brick.isBreakable(),
            "GUIDE-COL-004: GroundBrick không breakable");
        assertTrue(brick.isEmpty(),
            "GUIDE-COL-004: GroundBrick phải empty");
    }

    // ============================================================
    // 7. Performance Testing
    // ============================================================

    @Test
    @DisplayName("PERF-COL-001: checkAll với map đơn giản trong 100 lần")
    void perfCol001_checkAllPerformance() {
        collisionSystem = new CollisionSystem();
        Map map = new Map(300, null);
        Mario mario = new Mario(100, 300);
        map.setMario(mario);
        map.addGroundBrick(new GroundBrick(0, 380, null));

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            collisionSystem.checkAll(map, null);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 1000,
            "PERF-COL-001: 100 lần checkAll phải < 1000ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-COL-002: Camera.moveCam 1000 lần không lag")
    void perfCol002_cameraMoveCam1000Times() {
        Camera cam = new Camera();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            cam.moveCam(5, 0);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 100,
            "PERF-COL-002: 1000 lần moveCam phải < 100ms. Actual: " + elapsed + "ms");
    }
}
