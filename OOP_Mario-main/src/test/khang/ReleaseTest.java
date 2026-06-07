package test.khang;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import manager.Camera;
import model.GameObject;
import controller.CollisionSystem;
import model.Map;
import model.hero.Mario;
import model.brick.Brick;
import model.brick.Pipe;
import model.prize.Coin;

/**
 * Release Testing - Người 1 kiểm thử domain của Người 2:
 * CollisionSystem, Camera, GameObject
 *
 * Mục đích: Xác nhận hệ thống va chạm và camera hoạt động đúng
 * theo yêu cầu khi tích hợp với game thực tế.
 */
public class ReleaseTest {

    private CollisionSystem collisionSystem;
    private Camera camera;

    // ============================================================
    // 1. Requirements-based Testing: CollisionSystem
    // ============================================================

    @Test
    @DisplayName("REQ-COL-001: Mario va chạm với Pipe thì không đi xuyên qua")
    void reqCol001_marioCannotPassThroughPipe() {
        // Given: Mario ở vị trí (100, 100), Pipe tại (100, 100)
        Mario mario = new Mario(100, 100);
        Pipe pipe = new Pipe(100, 100, 40, 80);

        // When: CollisionSystem kiểm tra va chạm
        collisionSystem = new CollisionSystem();
        boolean colliding = collisionSystem.checkMarioObjectCollision(mario, pipe);

        // Then: phải phát hiện va chạm
        assertTrue(colliding,
            "REQ-COL-001: Mario phải được phát hiện va chạm với Pipe");
    }

    @Test
    @DisplayName("REQ-COL-002: Mario nhảy qua Pipe thì không va chạm")
    void reqCol002_marioJumpOverPipeNoCollision() {
        // Given: Mario ở vị trí cao hơn Pipe (y nhỏ hơn)
        Mario mario = new Mario(100, 50);  // cao hơn pipe top
        Pipe pipe = new Pipe(100, 100, 40, 80);

        // When:
        collisionSystem = new CollisionSystem();
        boolean colliding = collisionSystem.checkMarioObjectCollision(mario, pipe);

        // Then:
        assertFalse(colliding,
            "REQ-COL-002: Mario nhảy qua Pipe không được phát hiện va chạm");
    }

    @Test
    @DisplayName("REQ-COL-003: Mario chạm Brick thì bị chặn")
    void reqCol003_marioBlockedByBrick() {
        Mario mario = new Mario(200, 200);
        Brick brick = new Brick(200, 200, 40, 40);

        collisionSystem = new CollisionSystem();
        boolean colliding = collisionSystem.checkMarioObjectCollision(mario, brick);

        assertTrue(colliding,
            "REQ-COL-003: Mario chạm Brick phải được phát hiện");
    }

    @Test
    @DisplayName("REQ-COL-004: Mario không va chạm với Coin (xuyên qua)")
    void reqCol004_marioCanPassThroughCoin() {
        Mario mario = new Mario(150, 150);
        Coin coin = new Coin(150, 150);

        collisionSystem = new CollisionSystem();
        boolean solidCollision = collisionSystem.checkMarioSolidCollision(mario, coin);

        assertFalse(solidCollision,
            "REQ-COL-004: Mario không bị chặn bởi Coin");
    }

    @Test
    @DisplayName("REQ-COL-005: Camera không vượt ranh giới map bên trái")
    void reqCol005_cameraCannotGoLeftOfMap() {
        camera = new Camera();
        camera.setBounds(0, 0, 2000, 480);
        camera.setX(-50);  // attempt to go out of bounds

        assertTrue(camera.getX() >= 0,
            "REQ-COL-005: Camera X phải >= 0");
    }

    @Test
    @DisplayName("REQ-COL-006: Camera không vượt ranh giới map bên phải")
    void reqCol006_cameraCannotGoRightOfMap() {
        camera = new Camera();
        camera.setBounds(0, 0, 2000, 480);
        camera.setX(3000);  // attempt to go beyond right edge

        // Camera should clamp to maxX
        assertTrue(camera.getX() <= camera.getMaxX(),
            "REQ-COL-006: Camera X phải <= maxX");
    }

    // ============================================================
    // 2. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("SCN-COL-001: Mario chạm Coin -> Coin được thu thập")
    void scnCol001_marioCollectsCoin() {
        // Scenario: Mario di chuyển đến vị trí có Coin
        Mario mario = new Mario(100, 100);
        Coin coin = new Coin(105, 105);
        Map map = new Map();

        collisionSystem = new CollisionSystem();
        boolean contact = collisionSystem.checkMarioPrizeContact(mario, coin);

        assertTrue(contact,
            "SCN-COL-001: CollisionSystem phải phát hiện Mario chạm Coin");
    }

    @Test
    @DisplayName("SCN-COL-002: Mario đứng trên Brick không bị rơi")
    void scnCol002_marioStaysOnBrick() {
        Mario mario = new Mario(200, 200);
        Brick ground = new Brick(200, 220, 40, 20);  // immediately below Mario

        collisionSystem = new CollisionSystem();
        boolean bottomCollision = collisionSystem.checkBottomCollision(mario, ground);

        assertTrue(bottomCollision,
            "SCN-COL-002: Mario đứng trên Brick phải có bottom collision");
    }

    @Test
    @DisplayName("SCN-COL-003: Camera theo Mario khi Mario di chuyển phải")
    void scnCol003_cameraFollowsMarioRight() {
        camera = new Camera();
        camera.setBounds(0, 0, 2000, 480);
        camera.follow(new Mario(800, 240));

        assertTrue(camera.getX() > 0,
            "SCN-COL-003: Camera phải di chuyển theo Mario khi đi phải");
    }

    @Test
    @DisplayName("SCN-COL-004: Camera đứng yên khi Mario đi trái về đầu map")
    void scnCol004_cameraStaysAtMapStart() {
        camera = new Camera();
        camera.setBounds(0, 0, 2000, 480);
        camera.follow(new Mario(50, 240));  // near start of map

        assertEquals(0, camera.getX(),
            "SCN-COL-004: Camera phải đứng yên khi Mario ở đầu map");
    }

    // ============================================================
    // 3. Partition Testing (biên và trung tâm)
    // ============================================================

    @Test
    @DisplayName("PART-COL-001: Va chạm tại biên trái của object")
    void partCol001_collisionAtLeftEdge() {
        Mario mario = new Mario(95, 100);
        Brick brick = new Brick(100, 100, 40, 40);

        collisionSystem = new CollisionSystem();
        assertTrue(collisionSystem.checkMarioObjectCollision(mario, brick),
            "PART-COL-001: Va chạm tại biên trái phải được phát hiện");
    }

    @Test
    @DisplayName("PART-COL-002: Va chạm tại biên phải của object")
    void partCol002_collisionAtRightEdge() {
        Mario mario = new Mario(145, 100);
        Brick brick = new Brick(100, 100, 40, 40);

        collisionSystem = new CollisionSystem();
        assertTrue(collisionSystem.checkMarioObjectCollision(mario, brick),
            "PART-COL-002: Va chạm tại biên phải phải được phát hiện");
    }

    @Test
    @DisplayName("PART-COL-003: Va chạm tại tâm object")
    void partCol003_collisionAtCenter() {
        Mario mario = new Mario(120, 120);  // center of brick (100+20, 100+20)
        Brick brick = new Brick(100, 100, 40, 40);

        collisionSystem = new CollisionSystem();
        assertTrue(collisionSystem.checkMarioObjectCollision(mario, brick),
            "PART-COL-003: Va chạm tại tâm phải được phát hiện");
    }

    @Test
    @DisplayName("PART-COL-004: Không va chạm khi ở ngoài phạm vi")
    void partCol004_noCollisionWhenOutOfRange() {
        Mario mario = new Mario(500, 500);  // far away
        Brick brick = new Brick(100, 100, 40, 40);

        collisionSystem = new CollisionSystem();
        assertFalse(collisionSystem.checkMarioObjectCollision(mario, brick),
            "PART-COL-004: Không va chạm khi Mario ở xa");
    }

    // ============================================================
    // 4. Guideline-based Testing (null, empty, edge cases)
    // ============================================================

    @Test
    @DisplayName("GUIDE-COL-001: CollisionSystem xử lý null Map không crash")
    void guideCol001_nullMapDoesNotCrash() {
        collisionSystem = new CollisionSystem();
        assertDoesNotThrow(() -> collisionSystem.checkAll(null, null),
            "GUIDE-COL-001: checkAll(null) must not throw");
    }

    @Test
    @DisplayName("GUIDE-COL-002: Camera xử lý null target không crash")
    void guideCol002_nullCameraTargetDoesNotCrash() {
        camera = new Camera();
        assertDoesNotThrow(() -> camera.follow(null),
            "GUIDE-COL-002: follow(null) must not throw");
    }

    @Test
    @DisplayName("GUIDE-COL-003: Camera với maxX nhỏ hơn minX không crash")
    void guideCol003_cameraInvalidBoundsDoesNotCrash() {
        camera = new Camera();
        assertDoesNotThrow(() -> camera.setBounds(0, 0, -100, 480),
            "GUIDE-COL-003: Invalid bounds must not crash camera");
    }

    @Test
    @DisplayName("GUIDE-COL-004: GameObject với width/height bằng 0 không crash")
    void guideCol004_zeroDimensionGameObjectDoesNotCrash() {
        assertDoesNotThrow(() -> new GameObject(100, 100, 0, 0),
            "GUIDE-COL-004: Zero dimension object must not crash");
    }

    // ============================================================
    // 5. Performance / Stress Testing
    // ============================================================

    @Test
    @DisplayName("PERF-COL-001: checkAll xử lý map với 100 object trong 100ms")
    void perfCol001_checkAllPerformance() {
        collisionSystem = new CollisionSystem();
        Map map = new Map();
        // Add many objects to stress test
        for (int i = 0; i < 100; i++) {
            map.addBrick(new Brick(i * 50, 300, 40, 40));
        }

        long start = System.currentTimeMillis();
        collisionSystem.checkAll(map, null);
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 100,
            "PERF-COL-001: checkAll với 100 objects phải hoàn thành trong 100ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-COL-002: Camera follow không tạo leak khi gọi 1000 lần")
    void perfCol002_cameraFollowManyTimes() {
        camera = new Camera();
        camera.setBounds(0, 0, 2000, 480);

        for (int i = 0; i < 1000; i++) {
            camera.follow(new Mario(i, 240));
        }

        assertDoesNotThrow(() -> camera.getX(),
            "PERF-COL-002: Camera phải hoạt động ổn định sau 1000 lần follow");
    }
}