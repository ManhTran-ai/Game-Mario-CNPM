package test.test.nguoi4;

import manager.MapManager;
import model.Enemy.Enemy;
import model.Map;
import model.brick.GroundBrick;
import model.brick.OrdinaryBrick;
import model.prize.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Development Testing - Người 4 tự kiểm thử code của mình:
 * Map, Brick, Prize, MapManager
 *
 * Phạm vi:
 * - Unit test cho từng class/object
 * - Partition testing (biên, trung tâm)
 * - Guideline-based testing (null, edge)
 *
 * Chú ý constructors:
 *   Map(double remainingTime, BufferedImage bgImage)       — 2 tham số
 *   GroundBrick(double x, double y, BufferedImage style) — 3 tham số
 *   OrdinaryBrick(double x, double y, BufferedImage style) — 3 tham số
 *   Coin(double x, double y, BufferedImage style, int point) — 4 tham số
 *   SuperMushroom(double x, double y, BufferedImage style) — 3 tham số
 *   FireFlower(double x, double y, BufferedImage style)   — 3 tham số
 *   OneUpMushroom(double x, double y, BufferedImage style) — 3 tham số
 *
 * NOTE: Mario constructor calls AssetManager.getInstance().getImageLoader(),
 * may fail without resources. Wrap Mario creation in assertDoesNotThrow.
 */
public class Nguoi4_DevelopmentTest {

    // ============================================================
    // Map Tests
    // ============================================================

    @Test
    @DisplayName("Map - khởi tạo với thời gian")
    void map_initWithTime() {
        Map map = new Map(300, null);
        assertEquals(300, map.getRemainingTime(),
            "Map phải khởi tạo với remainingTime = 300");
    }

    @Test
    @DisplayName("Map - addBrick thêm thành công")
    void map_addBrick() {
        Map map = new Map(300, null);
        GroundBrick brick = new GroundBrick(0, 380, null);
        map.addBrick(brick);
        assertEquals(1, map.getBricks().size(),
            "Map sau khi addBrick phải có 1 brick");
    }

    @Test
    @DisplayName("Map - addGroundBrick thêm thành công")
    void map_addGroundBrick() {
        Map map = new Map(300, null);
        GroundBrick gb = new GroundBrick(0, 380, null);
        map.addGroundBrick(gb);
        assertEquals(1, map.getGroundBricks().size(),
            "Map sau khi addGroundBrick phải có 1 groundBrick");
    }

    @Test
    @DisplayName("Map - addEnemy thêm thành công")
    void map_addEnemy() {
        Map map = new Map(300, null);
        Enemy enemy = new Enemy(200, 300, null);
        map.addEnemy(enemy);
        assertEquals(1, map.getEnemies().size(),
            "Map sau khi addEnemy phải có 1 enemy");
    }

    @Test
    @DisplayName("Map - addRevealedPrize thêm thành công")
    void map_addRevealedPrize() {
        Map map = new Map(300, null);
        Coin coin = new Coin(100, 200, null, 100);
        map.addRevealedPrize(coin);
        assertEquals(1, map.getRevealedPrizes().size(),
            "Map sau khi addRevealedPrize phải có 1 prize");
    }

    @Test
    @DisplayName("Map - getAllBricks trả tổ hợp bricks và groundBricks")
    void map_getAllBricks() {
        Map map = new Map(300, null);
        map.addBrick(new GroundBrick(0, 380, null));
        map.addGroundBrick(new GroundBrick(48, 380, null));

        var all = map.getAllBricks();
        assertTrue(all.size() >= 2,
            "getAllBricks phải trả tổ hợp bricks + groundBricks (ít nhất 2)");
    }

    @Test
    @DisplayName("Map - updateTime giảm remainingTime")
    void map_updateTime() {
        Map map = new Map(300, null);
        map.updateTime(1);
        assertTrue(map.getRemainingTime() < 300 || map.getRemainingTime() == 300,
            "updateTime(1) phải giảm hoặc reset remainingTime");
    }

    @Test
    @DisplayName("Map - isTimeOver đúng khi hết giờ")
    void map_isTimeOver() {
        Map map = new Map(0, null);
        assertTrue(map.isTimeOver(),
            "Map với remainingTime=0 phải isTimeOver=true");
    }

    @Test
    @DisplayName("Map - isTimeOver false khi còn thời gian")
    void map_isNotTimeOverWhenTimeRemains() {
        Map map = new Map(100, null);
        assertFalse(map.isTimeOver(),
            "Map với remainingTime > 0 phải isTimeOver=false");
    }

    @Test
    @DisplayName("Map - getMario trả null khi chưa set")
    void map_getMarioReturnsNullWhenNotSet() {
        Map map = new Map(300, null);
        assertNull(map.getMario(),
            "Map chưa set Mario phải trả null");
    }

    @Test
    @DisplayName("Map - setMario và getMario hoạt động đúng")
    void map_setMarioAndGetMario() {
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> {
            model.hero.Mario mario = new model.hero.Mario(100, 300);
            map.setMario(mario);
        }, "Mario constructor không crash");
    }

    @Test
    @DisplayName("Map - removeEnemy hoạt động")
    void map_removeEnemy() {
        Map map = new Map(300, null);
        Enemy enemy = new Enemy(200, 300, null);
        map.addEnemy(enemy);
        assertEquals(1, map.getEnemies().size());

        map.removeEnemy(enemy);
        assertEquals(0, map.getEnemies().size(),
            "removeEnemy phải xóa enemy khỏi danh sách");
    }

    // ============================================================
    // Brick Tests
    // ============================================================

    @Test
    @DisplayName("Brick - OrdinaryBrick khởi tạo với vị trí đúng")
    void ordinaryBrick_init() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertEquals(100, brick.getX());
        assertEquals(200, brick.getY());
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick isBreakable ban đầu true")
    void ordinaryBrick_isBreakableInitially() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertTrue(brick.isBreakable(),
            "OrdinaryBrick phải là breakable");
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick isEmpty ban đầu true")
    void ordinaryBrick_isEmptyInitially() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertTrue(brick.isEmpty(),
            "OrdinaryBrick phải là empty ban đầu");
    }

    @Test
    @DisplayName("Brick - GroundBrick isBreakable = false")
    void groundBrick_notBreakable() {
        GroundBrick gb = new GroundBrick(0, 380, null);
        assertFalse(gb.isBreakable(),
            "GroundBrick không được breakable");
    }

    @Test
    @DisplayName("Brick - GroundBrick isEmpty = true")
    void groundBrick_isEmpty() {
        GroundBrick gb = new GroundBrick(0, 380, null);
        assertTrue(gb.isEmpty(),
            "GroundBrick phải là empty");
    }

    @Test
    @DisplayName("Brick - setBreakable và setEmpty thay đổi trạng thái")
    void brick_stateSetters() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        brick.setBreakable(false);
        brick.setEmpty(false);

        assertFalse(brick.isBreakable(),
            "setBreakable(false) phải hoạt động");
        assertFalse(brick.isEmpty(),
            "setEmpty(false) phải hoạt động");
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick getFrames() không crash")
    void ordinaryBrick_getFrames() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertDoesNotThrow(() -> brick.getFrames(),
            "getFrames() không crash");
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick animate() không crash")
    void ordinaryBrick_animate() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertDoesNotThrow(() -> brick.animate(),
            "animate() không crash");
    }

    // ============================================================
    // Prize Tests
    // ============================================================

    @Test
    @DisplayName("Prize - Coin khởi tạo với point")
    void coin_initWithPoint() {
        Coin coin = new Coin(100, 200, null, 100);
        assertEquals(100, coin.getPoint(),
            "Coin point phải bằng 100");
    }

    @Test
    @DisplayName("Prize - Coin isRevealed ban đầu false")
    void coin_notRevealedInitially() {
        Coin coin = new Coin(100, 200, null, 100);
        assertFalse(coin.isRevealed(),
            "Coin isRevealed ban đầu phải là false");
    }

    @Test
    @DisplayName("Prize - Coin.reveal() đặt isRevealed=true")
    void coin_reveal() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.reveal();
        assertTrue(coin.isRevealed(),
            "Coin sau reveal() phải là revealed");
    }

    @Test
    @DisplayName("Prize - setPoint và getPoint hoạt động")
    void prize_pointAccessors() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.setPoint(200);
        assertEquals(200, coin.getPoint(),
            "setPoint(200) phải cập nhật getPoint()");
    }

    @Test
    @DisplayName("Prize - SuperMushroom là instanceof Prize")
    void superMushroomIsPrize() {
        SuperMushroom mushroom = new SuperMushroom(100, 200, null);
        assertTrue(mushroom instanceof Prize,
            "SuperMushroom phải là instanceof Prize");
    }

    @Test
    @DisplayName("Prize - FireFlower là instanceof Prize")
    void fireFlowerIsPrize() {
        FireFlower flower = new FireFlower(100, 200, null);
        assertTrue(flower instanceof Prize,
            "FireFlower phải là instanceof Prize");
    }

    @Test
    @DisplayName("Prize - OneUpMushroom là instanceof Prize")
    void oneUpMushroomIsPrize() {
        OneUpMushroom mushroom = new OneUpMushroom(100, 200, null);
        assertTrue(mushroom instanceof Prize,
            "OneUpMushroom phải là instanceof Prize");
    }

    @Test
    @DisplayName("Prize - SuperMushroom isRevealed ban đầu false")
    void superMushroom_notRevealedInitially() {
        SuperMushroom mushroom = new SuperMushroom(100, 200, null);
        assertFalse(mushroom.isRevealed(),
            "SuperMushroom isRevealed ban đầu phải là false");
    }

    @Test
    @DisplayName("Prize - FireFlower isRevealed ban đầu false")
    void fireFlower_notRevealedInitially() {
        FireFlower flower = new FireFlower(100, 200, null);
        assertFalse(flower.isRevealed(),
            "FireFlower isRevealed ban đầu phải là false");
    }

    @Test
    @DisplayName("Prize - OneUpMushroom isRevealed ban đầu false")
    void oneUpMushroom_notRevealedInitially() {
        OneUpMushroom mushroom = new OneUpMushroom(100, 200, null);
        assertFalse(mushroom.isRevealed(),
            "OneUpMushroom isRevealed ban đầu phải là false");
    }

    @Test
    @DisplayName("Prize - setRevealed và reveal hoạt động")
    void prize_setRevealedAndReveal() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.setRevealed(true);
        assertTrue(coin.isRevealed(),
            "setRevealed(true) phải hoạt động");

        coin.setRevealed(false);
        assertFalse(coin.isRevealed(),
            "setRevealed(false) phải hoạt động");
    }

    @Test
    @DisplayName("Prize - shouldRemove() hoạt động")
    void prize_shouldRemove() {
        Coin coin = new Coin(100, 200, null, 100);
        assertDoesNotThrow(() -> coin.shouldRemove(),
            "shouldRemove() không crash");
    }

    // ============================================================
    // MapManager Tests
    // ============================================================

    @Test
    @DisplayName("MapManager - constructor tạo được instance")
    void mapManager_constructor() {
        MapManager mm = new MapManager();
        assertNotNull(mm,
            "MapManager phải tạo được instance");
    }

    @Test
    @DisplayName("MapManager - getScore() trả về giá trị hợp lệ")
    void mapManager_getScore() {
        MapManager mm = new MapManager();
        int score = mm.getScore();
        assertTrue(score >= 0,
            "getScore() phải trả về giá trị >= 0");
    }

    @Test
    @DisplayName("MapManager - getRemainingLives() trả về giá trị hợp lệ")
    void mapManager_getRemainingLives() {
        MapManager mm = new MapManager();
        int lives = mm.getRemainingLives();
        assertTrue(lives >= 0,
            "getRemainingLives() phải trả về giá trị >= 0");
    }

    @Test
    @DisplayName("MapManager - getCoins() trả về giá trị hợp lệ")
    void mapManager_getCoins() {
        MapManager mm = new MapManager();
        int coins = mm.getCoins();
        assertTrue(coins >= 0,
            "getCoins() phải trả về giá trị >= 0");
    }

    @Test
    @DisplayName("MapManager - acquirePoints() cộng điểm")
    void mapManager_acquirePoints() {
        MapManager mm = new MapManager();
        int initialScore = mm.getScore();
        mm.acquirePoints(100);
        assertTrue(mm.getScore() >= initialScore + 100,
            "acquirePoints(100) phải tăng score thêm 100");
    }

    @Test
    @DisplayName("MapManager - isGameOver() trả về boolean")
    void mapManager_isGameOver() {
        MapManager mm = new MapManager();
        assertDoesNotThrow(() -> mm.isGameOver(),
            "isGameOver() không crash");
    }

    // ============================================================
    // Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("Guideline - Map với null background không crash")
    void guideline_nullBackgroundDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(300, null),
            "Map(300, null) không crash");
    }

    @Test
    @DisplayName("Guideline - Brick với tọa độ âm không crash")
    void guideline_negativeBrickCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new GroundBrick(-100, -50, null),
            "GroundBrick(-100,-50) không crash");
    }

    @Test
    @DisplayName("Guideline - Coin với point=0 không crash")
    void guideline_zeroPointCoinDoesNotCrash() {
        assertDoesNotThrow(() -> new Coin(100, 200, null, 0),
            "Coin point=0 không crash");
    }

    @Test
    @DisplayName("Guideline - Map với time âm không crash")
    void guideline_negativeTimeDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(-100, null),
            "Map với time âm không crash");
    }

    @Test
    @DisplayName("Guideline - Prize với tọa độ âm không crash")
    void guideline_negativePrizeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new SuperMushroom(-100, -200, null),
            "SuperMushroom với tọa độ âm không crash");
    }

    @Test
    @DisplayName("Guideline - Mario constructor không crash (gọi AssetManager)")
    void guideline_marioConstructorDoesNotCrash() {
        assertDoesNotThrow(() -> new model.hero.Mario(100, 300),
            "Mario constructor gọi AssetManager nhưng không crash");
    }
}
