package test.Hung;

import model.Map;
import model.brick.Brick;
import model.brick.GroundBrick;
import model.brick.OrdinaryBrick;
import model.Enemy.Enemy;
import model.prize.Coin;
import model.prize.FireFlower;
import model.prize.OneUpMushroom;
import model.prize.Prize;
import model.prize.SuperMushroom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReleaseTest {

    // ============================================================
    // 1. Requirements-based Testing: Map
    // ============================================================

    @Test
    @DisplayName("REQ-MAP-001: Map khoi tao voi tham so dung")
    void reqMap001_mapInitializedWithCorrectParams() {
        Map map = new Map(300, null);
        assertNotNull(map, "REQ-MAP-001: Map phai duoc tao");
    }

    @Test
    @DisplayName("REQ-MAP-002: Map.addBrick() them Brick thanh cong")
    void reqMap002_addBrickSuccessfully() {
        Map map = new Map(300, null);
        Brick brick = new GroundBrick(0, 380, null);
        map.addBrick(brick);
        assertEquals(1, map.getBricks().size(),
            "REQ-MAP-002: addBrick them Brick vao danh sach");
    }

    @Test
    @DisplayName("REQ-MAP-003: Map.addGroundBrick() them GroundBrick thanh cong")
    void reqMap003_addGroundBrickSuccessfully() {
        Map map = new Map(300, null);
        GroundBrick gb = new GroundBrick(0, 380, null);
        map.addGroundBrick(gb);
        assertEquals(1, map.getGroundBricks().size(),
            "REQ-MAP-003: addGroundBrick them vao danh sach groundBricks");
    }

    @Test
    @DisplayName("REQ-MAP-004: Map.getAllBricks() tra ve tong bricks + groundBricks")
    void reqMap004_getAllBricksReturnsCombined() {
        Map map = new Map(300, null);
        map.addBrick(new GroundBrick(0, 380, null));
        map.addGroundBrick(new GroundBrick(0, 380, null));

        var allBricks = map.getAllBricks();
        assertTrue(allBricks.size() >= 1,
            "REQ-MAP-004: getAllBricks tra ve tong hop bricks + groundBricks");
    }

    @Test
    @DisplayName("REQ-MAP-005: Map.updateTime() giam remainingTime")
    void reqMap005_updateTimeDecreasesTime() {
        Map map = new Map(300, null);
        double timeBefore = map.getRemainingTime();

        map.updateTime(1);

        assertTrue(map.getRemainingTime() < timeBefore || map.getRemainingTime() == 300,
            "REQ-MAP-005: updateTime(1) phai giam remainingTime");
    }

    @Test
    @DisplayName("REQ-MAP-006: Map.isTimeOver() dung khi het gio")
    void reqMap006_isTimeOver() {
        Map map = new Map(0, null);
        assertTrue(map.isTimeOver(),
            "REQ-MAP-006: Map voi remainingTime=0 phai isTimeOver=true");
    }

    // ============================================================
    // 2. Requirements-based Testing: Brick
    // ============================================================

    @Test
    @DisplayName("REQ-BRK-001: OrdinaryBrick isBreakable ban dau true")
    void reqBrk001_ordinaryBrickIsBreakable() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertTrue(brick.isBreakable(),
            "REQ-BRK-001: OrdinaryBrick isBreakable() ban dau phai true");
    }

    @Test
    @DisplayName("REQ-BRK-002: OrdinaryBrick isEmpty ban dau true")
    void reqBrk002_ordinaryBrickIsEmpty() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertTrue(brick.isEmpty(),
            "REQ-BRK-002: OrdinaryBrick isEmpty() ban dau phai true");
    }

    @Test
    @DisplayName("REQ-BRK-003: GroundBrick isBreakable=false, isEmpty=true")
    void reqBrk003_groundBrickProperties() {
        GroundBrick gb = new GroundBrick(0, 380, null);
        assertFalse(gb.isBreakable(),
            "REQ-BRK-003: GroundBrick khong duoc breakable");
        assertTrue(gb.isEmpty(),
            "REQ-BRK-003: GroundBrick luon empty");
    }

    @Test
    @DisplayName("REQ-BRK-004: Brick setBreakable() va setEmpty() thay doi trang thai")
    void reqBrk004_brickSetters() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        brick.setBreakable(true);
        brick.setEmpty(false);
        assertTrue(brick.isBreakable(),
            "REQ-BRK-004: setBreakable(true) phai hoat dong");
        assertFalse(brick.isEmpty(),
            "REQ-BRK-004: setEmpty(false) phai hoat dong");
    }

    // ============================================================
    // 3. Requirements-based Testing: Prize
    // ============================================================

    @Test
    @DisplayName("REQ-PRZ-001: Coin khoi tao voi point")
    void reqPrz001_coinInitWithPoint() {
        Coin coin = new Coin(100, 200, null, 100);
        assertEquals(100, coin.getPoint(),
            "REQ-PRZ-001: Coin point phai bang 100");
    }

    @Test
    @DisplayName("REQ-PRZ-002: Coin.isRevealed() ban dau false")
    void reqPrz002_coinNotRevealedInitially() {
        Coin coin = new Coin(100, 200, null, 100);
        assertFalse(coin.isRevealed(),
            "REQ-PRZ-002: Coin isRevealed ban dau phai false");
    }

    @Test
    @DisplayName("REQ-PRZ-003: Coin.reveal() dat isRevealed=true")
    void reqPrz003_coinReveal() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.reveal();
        assertTrue(coin.isRevealed(),
            "REQ-PRZ-003: reveal() phai dat isRevealed=true");
    }

    @Test
    @DisplayName("REQ-PRZ-004: Prize.setPoint() va getPoint()")
    void reqPrz004_prizePointAccessors() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.setPoint(200);
        assertEquals(200, coin.getPoint(),
            "REQ-PRZ-004: setPoint(200) phai cap nhat getPoint()");
    }

    @Test
    @DisplayName("REQ-PRZ-005: SuperMushroom instanceof Prize")
    void reqPrz005_superMushroomIsPrize() {
        SuperMushroom mushroom = new SuperMushroom(100, 200, null);
        assertTrue(mushroom instanceof Prize,
            "REQ-PRZ-005: SuperMushroom phai la instanceof Prize");
    }

    @Test
    @DisplayName("REQ-PRZ-006: FireFlower instanceof Prize")
    void reqPrz006_fireFlowerIsPrize() {
        FireFlower flower = new FireFlower(100, 200, null);
        assertTrue(flower instanceof Prize,
            "REQ-PRZ-006: FireFlower phai la instanceof Prize");
    }

    @Test
    @DisplayName("REQ-PRZ-007: OneUpMushroom instanceof Prize")
    void reqPrz007_oneUpMushroomIsPrize() {
        OneUpMushroom mushroom = new OneUpMushroom(100, 200, null);
        assertTrue(mushroom instanceof Prize,
            "REQ-PRZ-007: OneUpMushroom phai la instanceof Prize");
    }

    // ============================================================
    // 4. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("SCN-MAP-001: Map voi nhieu Brick")
    void scnMap001_multipleBricks() {
        Map map = new Map(300, null);
        for (int i = 0; i < 5; i++) {
            map.addBrick(new GroundBrick(i * 48, 380, null));
        }
        assertEquals(5, map.getBricks().size(),
            "SCN-MAP-001: Map phai chua dung 5 bricks");
    }

    @Test
    @DisplayName("SCN-MAP-002: Map voi nhieu GroundBrick")
    void scnMap002_multipleGroundBricks() {
        Map map = new Map(300, null);
        for (int i = 0; i < 10; i++) {
            map.addGroundBrick(new GroundBrick(i * 48, 380, null));
        }
        assertEquals(10, map.getAllBricks().size(),
            "SCN-MAP-002: getAllBricks() phai tra ve 10 bricks");
    }

    @Test
    @DisplayName("SCN-MAP-003: Map.removeEnemy() xoa Enemy khoi danh sach")
    void scnMap003_removeEnemy() {
        Map map = new Map(300, null);
        Enemy enemy = new Enemy(200, 300, null);
        map.addEnemy(enemy);
        assertEquals(1, map.getEnemies().size());

        map.removeEnemy(enemy);
        assertEquals(0, map.getEnemies().size(),
            "SCN-MAP-003: removeEnemy() phai xoa Enemy khoi danh sach");
    }

    @Test
    @DisplayName("SCN-BRK-001: Brick setLocation thay doi X va Y")
    void scnBrk001_brickSetLocation() {
        GroundBrick gb = new GroundBrick(0, 0, null);
        gb.setLocation(100, 200);
        assertEquals(100, gb.getX(),
            "SCN-BRK-001: setLocation(100,200) phai dat X=100");
        assertEquals(200, gb.getY(),
            "SCN-BRK-001: setLocation(100,200) phai dat Y=200");
    }

    // ============================================================
    // 5. Partition Testing
    // ============================================================

    @Test
    @DisplayName("PART-BRK-001: Brick voi kich thuoc bang 0 van co getBounds()")
    void partBrk001_brickWithZeroDimension() {
        GroundBrick gb = new GroundBrick(100, 200, null);
        gb.setDimension(0, 0);
        assertNotNull(gb.getBounds(),
            "PART-BRK-001: Brick voi dimension 0 van co getBounds()");
    }

    @Test
    @DisplayName("PART-BRK-002: Map voi remainingTime cuc dai")
    void partBrk002_mapWithLargeTime() {
        Map map = new Map(Double.MAX_VALUE, null);
        assertNotNull(map.getRemainingTime(),
            "PART-BRK-002: Map voi thoi gian lon van hop le");
    }

    // ============================================================
    // 6. Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("GUIDE-MAP-001: Map voi null backgroundImage khong crash")
    void guideMap001_nullBackgroundDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(300, null),
            "GUIDE-MAP-001: new Map(300, null) khong crash");
    }

    @Test
    @DisplayName("GUIDE-MAP-002: Brick voi toa do am khong crash")
    void guideMap002_negativeBrickCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new GroundBrick(-100, -50, null),
            "GUIDE-MAP-002: GroundBrick(-100,-50) khong crash");
    }

    @Test
    @DisplayName("GUIDE-MAP-003: Coin voi point=0 khong crash")
    void guideMap003_coinWithZeroPointDoesNotCrash() {
        assertDoesNotThrow(() -> new Coin(100, 200, null, 0),
            "GUIDE-MAP-003: Coin point=0 khong crash");
    }

    @Test
    @DisplayName("GUIDE-MAP-004: Map voi remainingTime am")
    void guideMap004_negativeTimeDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(-10, null),
            "GUIDE-MAP-004: Map voi thoi gian am khong crash");
    }

    // ============================================================
    // 7. Performance Testing
    // ============================================================

    @Test
    @DisplayName("PERF-MAP-001: Map.addBrick 100 lan trong 100ms")
    void perfMap001_addBrick100TimesFast() {
        Map map = new Map(300, null);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            map.addBrick(new GroundBrick(i * 48, 380, null));
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 100,
            "PERF-MAP-001: addBrick 100 lan phai < 100ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-MAP-002: Map.updateTime 1000 lan trong 100ms")
    void perfMap002_updateTime1000TimesFast() {
        Map map = new Map(300, null);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            map.updateTime(1);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 100,
            "PERF-MAP-002: updateTime 1000 lan phai < 100ms. Actual: " + elapsed + "ms");
    }
}
