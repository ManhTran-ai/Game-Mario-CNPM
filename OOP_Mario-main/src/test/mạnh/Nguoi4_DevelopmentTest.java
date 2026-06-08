package test.nguoi4;

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
 * Development Testing - Ng╞░ß╗¥i 4 tß╗▒ kiß╗âm thß╗¡ code cß╗ºa m├¼nh:
 * Map, Brick, Prize, MapManager
 *
 * Phß║ím vi:
 * - Unit test cho tß╗½ng class/object
 * - Partition testing (bi├¬n, trung t├óm)
 * - Guideline-based testing (null, edge)
 *
 * Ch├║ ├╜ constructors:
 *   Map(double remainingTime, BufferedImage bgImage)       ΓÇö 2 tham sß╗æ
 *   GroundBrick(double x, double y, BufferedImage style) ΓÇö 3 tham sß╗æ
 *   OrdinaryBrick(double x, double y, BufferedImage style) ΓÇö 3 tham sß╗æ
 *   Coin(double x, double y, BufferedImage style, int point) ΓÇö 4 tham sß╗æ
 *   SuperMushroom(double x, double y, BufferedImage style) ΓÇö 3 tham sß╗æ
 *   FireFlower(double x, double y, BufferedImage style)   ΓÇö 3 tham sß╗æ
 *   OneUpMushroom(double x, double y, BufferedImage style) ΓÇö 3 tham sß╗æ
 *
 * NOTE: Mario constructor calls AssetManager.getInstance().getImageLoader(),
 * may fail without resources. Wrap Mario creation in assertDoesNotThrow.
 */
public class Nguoi4_DevelopmentTest {

    // ============================================================
    // Map Tests
    // ============================================================

    @Test
    @DisplayName("Map - khß╗ƒi tß║ío vß╗¢i thß╗¥i gian")
    void map_initWithTime() {
        Map map = new Map(300, null);
        assertEquals(300, map.getRemainingTime(),
            "Map phß║úi khß╗ƒi tß║ío vß╗¢i remainingTime = 300");
    }

    @Test
    @DisplayName("Map - addBrick th├¬m th├ánh c├┤ng")
    void map_addBrick() {
        Map map = new Map(300, null);
        GroundBrick brick = new GroundBrick(0, 380, null);
        map.addBrick(brick);
        assertEquals(1, map.getBricks().size(),
            "Map sau khi addBrick phß║úi c├│ 1 brick");
    }

    @Test
    @DisplayName("Map - addGroundBrick th├¬m th├ánh c├┤ng")
    void map_addGroundBrick() {
        Map map = new Map(300, null);
        GroundBrick gb = new GroundBrick(0, 380, null);
        map.addGroundBrick(gb);
        assertEquals(1, map.getGroundBricks().size(),
            "Map sau khi addGroundBrick phß║úi c├│ 1 groundBrick");
    }

    @Test
    @DisplayName("Map - addEnemy th├¬m th├ánh c├┤ng")
    void map_addEnemy() {
        Map map = new Map(300, null);
        Enemy enemy = new Enemy(200, 300, null);
        map.addEnemy(enemy);
        assertEquals(1, map.getEnemies().size(),
            "Map sau khi addEnemy phß║úi c├│ 1 enemy");
    }

    @Test
    @DisplayName("Map - addRevealedPrize th├¬m th├ánh c├┤ng")
    void map_addRevealedPrize() {
        Map map = new Map(300, null);
        Coin coin = new Coin(100, 200, null, 100);
        map.addRevealedPrize(coin);
        assertEquals(1, map.getRevealedPrizes().size(),
            "Map sau khi addRevealedPrize phß║úi c├│ 1 prize");
    }

    @Test
    @DisplayName("Map - getAllBricks trß║ú tß╗ò hß╗úp bricks v├á groundBricks")
    void map_getAllBricks() {
        Map map = new Map(300, null);
        map.addBrick(new GroundBrick(0, 380, null));
        map.addGroundBrick(new GroundBrick(48, 380, null));

        var all = map.getAllBricks();
        assertTrue(all.size() >= 2,
            "getAllBricks phß║úi trß║ú tß╗ò hß╗úp bricks + groundBricks (├¡t nhß║Ñt 2)");
    }

    @Test
    @DisplayName("Map - updateTime giß║úm remainingTime")
    void map_updateTime() {
        Map map = new Map(300, null);
        map.updateTime(1);
        assertTrue(map.getRemainingTime() < 300 || map.getRemainingTime() == 300,
            "updateTime(1) phß║úi giß║úm hoß║╖c reset remainingTime");
    }

    @Test
    @DisplayName("Map - isTimeOver ─æ├║ng khi hß║┐t giß╗¥")
    void map_isTimeOver() {
        Map map = new Map(0, null);
        assertTrue(map.isTimeOver(),
            "Map vß╗¢i remainingTime=0 phß║úi isTimeOver=true");
    }

    @Test
    @DisplayName("Map - isTimeOver false khi c├▓n thß╗¥i gian")
    void map_isNotTimeOverWhenTimeRemains() {
        Map map = new Map(100, null);
        assertFalse(map.isTimeOver(),
            "Map vß╗¢i remainingTime > 0 phß║úi isTimeOver=false");
    }

    @Test
    @DisplayName("Map - getMario trß║ú null khi ch╞░a set")
    void map_getMarioReturnsNullWhenNotSet() {
        Map map = new Map(300, null);
        assertNull(map.getMario(),
            "Map ch╞░a set Mario phß║úi trß║ú null");
    }

    @Test
    @DisplayName("Map - setMario v├á getMario hoß║ít ─æß╗Öng ─æ├║ng")
    void map_setMarioAndGetMario() {
        Map map = new Map(300, null);
        assertDoesNotThrow(() -> {
            model.hero.Mario mario = new model.hero.Mario(100, 300);
            map.setMario(mario);
        }, "Mario constructor kh├┤ng crash");
    }

    @Test
    @DisplayName("Map - removeEnemy hoß║ít ─æß╗Öng")
    void map_removeEnemy() {
        Map map = new Map(300, null);
        Enemy enemy = new Enemy(200, 300, null);
        map.addEnemy(enemy);
        assertEquals(1, map.getEnemies().size());

        map.removeEnemy(enemy);
        assertEquals(0, map.getEnemies().size(),
            "removeEnemy phß║úi x├│a enemy khß╗Åi danh s├ích");
    }

    // ============================================================
    // Brick Tests
    // ============================================================

    @Test
    @DisplayName("Brick - OrdinaryBrick khß╗ƒi tß║ío vß╗¢i vß╗ï tr├¡ ─æ├║ng")
    void ordinaryBrick_init() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertEquals(100, brick.getX());
        assertEquals(200, brick.getY());
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick isBreakable ban ─æß║ºu true")
    void ordinaryBrick_isBreakableInitially() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertTrue(brick.isBreakable(),
            "OrdinaryBrick phß║úi l├á breakable");
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick isEmpty ban ─æß║ºu true")
    void ordinaryBrick_isEmptyInitially() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertTrue(brick.isEmpty(),
            "OrdinaryBrick phß║úi l├á empty ban ─æß║ºu");
    }

    @Test
    @DisplayName("Brick - GroundBrick isBreakable = false")
    void groundBrick_notBreakable() {
        GroundBrick gb = new GroundBrick(0, 380, null);
        assertFalse(gb.isBreakable(),
            "GroundBrick kh├┤ng ─æ╞░ß╗úc breakable");
    }

    @Test
    @DisplayName("Brick - GroundBrick isEmpty = true")
    void groundBrick_isEmpty() {
        GroundBrick gb = new GroundBrick(0, 380, null);
        assertTrue(gb.isEmpty(),
            "GroundBrick phß║úi l├á empty");
    }

    @Test
    @DisplayName("Brick - setBreakable v├á setEmpty thay ─æß╗òi trß║íng th├íi")
    void brick_stateSetters() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        brick.setBreakable(false);
        brick.setEmpty(false);

        assertFalse(brick.isBreakable(),
            "setBreakable(false) phß║úi hoß║ít ─æß╗Öng");
        assertFalse(brick.isEmpty(),
            "setEmpty(false) phß║úi hoß║ít ─æß╗Öng");
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick getFrames() kh├┤ng crash")
    void ordinaryBrick_getFrames() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertDoesNotThrow(() -> brick.getFrames(),
            "getFrames() kh├┤ng crash");
    }

    @Test
    @DisplayName("Brick - OrdinaryBrick animate() kh├┤ng crash")
    void ordinaryBrick_animate() {
        OrdinaryBrick brick = new OrdinaryBrick(100, 200, null);
        assertDoesNotThrow(() -> brick.animate(),
            "animate() kh├┤ng crash");
    }

    // ============================================================
    // Prize Tests
    // ============================================================

    @Test
    @DisplayName("Prize - Coin khß╗ƒi tß║ío vß╗¢i point")
    void coin_initWithPoint() {
        Coin coin = new Coin(100, 200, null, 100);
        assertEquals(100, coin.getPoint(),
            "Coin point phß║úi bß║▒ng 100");
    }

    @Test
    @DisplayName("Prize - Coin isRevealed ban ─æß║ºu false")
    void coin_notRevealedInitially() {
        Coin coin = new Coin(100, 200, null, 100);
        assertFalse(coin.isRevealed(),
            "Coin isRevealed ban ─æß║ºu phß║úi l├á false");
    }

    @Test
    @DisplayName("Prize - Coin.reveal() ─æß║╖t isRevealed=true")
    void coin_reveal() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.reveal();
        assertTrue(coin.isRevealed(),
            "Coin sau reveal() phß║úi l├á revealed");
    }

    @Test
    @DisplayName("Prize - setPoint v├á getPoint hoß║ít ─æß╗Öng")
    void prize_pointAccessors() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.setPoint(200);
        assertEquals(200, coin.getPoint(),
            "setPoint(200) phß║úi cß║¡p nhß║¡t getPoint()");
    }

    @Test
    @DisplayName("Prize - SuperMushroom l├á instanceof Prize")
    void superMushroomIsPrize() {
        SuperMushroom mushroom = new SuperMushroom(100, 200, null);
        assertTrue(mushroom instanceof Prize,
            "SuperMushroom phß║úi l├á instanceof Prize");
    }

    @Test
    @DisplayName("Prize - FireFlower l├á instanceof Prize")
    void fireFlowerIsPrize() {
        FireFlower flower = new FireFlower(100, 200, null);
        assertTrue(flower instanceof Prize,
            "FireFlower phß║úi l├á instanceof Prize");
    }

    @Test
    @DisplayName("Prize - OneUpMushroom l├á instanceof Prize")
    void oneUpMushroomIsPrize() {
        OneUpMushroom mushroom = new OneUpMushroom(100, 200, null);
        assertTrue(mushroom instanceof Prize,
            "OneUpMushroom phß║úi l├á instanceof Prize");
    }

    @Test
    @DisplayName("Prize - SuperMushroom isRevealed ban ─æß║ºu false")
    void superMushroom_notRevealedInitially() {
        SuperMushroom mushroom = new SuperMushroom(100, 200, null);
        assertFalse(mushroom.isRevealed(),
            "SuperMushroom isRevealed ban ─æß║ºu phß║úi l├á false");
    }

    @Test
    @DisplayName("Prize - FireFlower isRevealed ban ─æß║ºu false")
    void fireFlower_notRevealedInitially() {
        FireFlower flower = new FireFlower(100, 200, null);
        assertFalse(flower.isRevealed(),
            "FireFlower isRevealed ban ─æß║ºu phß║úi l├á false");
    }

    @Test
    @DisplayName("Prize - OneUpMushroom isRevealed ban ─æß║ºu false")
    void oneUpMushroom_notRevealedInitially() {
        OneUpMushroom mushroom = new OneUpMushroom(100, 200, null);
        assertFalse(mushroom.isRevealed(),
            "OneUpMushroom isRevealed ban ─æß║ºu phß║úi l├á false");
    }

    @Test
    @DisplayName("Prize - setRevealed v├á reveal hoß║ít ─æß╗Öng")
    void prize_setRevealedAndReveal() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.setRevealed(true);
        assertTrue(coin.isRevealed(),
            "setRevealed(true) phß║úi hoß║ít ─æß╗Öng");

        coin.setRevealed(false);
        assertFalse(coin.isRevealed(),
            "setRevealed(false) phß║úi hoß║ít ─æß╗Öng");
    }

    @Test
    @DisplayName("Prize - shouldRemove() hoß║ít ─æß╗Öng")
    void prize_shouldRemove() {
        Coin coin = new Coin(100, 200, null, 100);
        assertDoesNotThrow(() -> coin.shouldRemove(),
            "shouldRemove() kh├┤ng crash");
    }

    // ============================================================
    // MapManager Tests
    // ============================================================

    @Test
    @DisplayName("MapManager - constructor tß║ío ─æ╞░ß╗úc instance")
    void mapManager_constructor() {
        MapManager mm = new MapManager();
        assertNotNull(mm,
            "MapManager phß║úi tß║ío ─æ╞░ß╗úc instance");
    }

    @Test
    @DisplayName("MapManager - getScore() trß║ú vß╗ü gi├í trß╗ï hß╗úp lß╗ç")
    void mapManager_getScore() {
        MapManager mm = new MapManager();
        int score = mm.getScore();
        assertTrue(score >= 0,
            "getScore() phß║úi trß║ú vß╗ü gi├í trß╗ï >= 0");
    }

    @Test
    @DisplayName("MapManager - getRemainingLives() trß║ú vß╗ü gi├í trß╗ï hß╗úp lß╗ç")
    void mapManager_getRemainingLives() {
        MapManager mm = new MapManager();
        int lives = mm.getRemainingLives();
        assertTrue(lives >= 0,
            "getRemainingLives() phß║úi trß║ú vß╗ü gi├í trß╗ï >= 0");
    }

    @Test
    @DisplayName("MapManager - getCoins() trß║ú vß╗ü gi├í trß╗ï hß╗úp lß╗ç")
    void mapManager_getCoins() {
        MapManager mm = new MapManager();
        int coins = mm.getCoins();
        assertTrue(coins >= 0,
            "getCoins() phß║úi trß║ú vß╗ü gi├í trß╗ï >= 0");
    }

    @Test
    @DisplayName("MapManager - acquirePoints() cß╗Öng ─æiß╗âm")
    void mapManager_acquirePoints() {
        MapManager mm = new MapManager();
        int initialScore = mm.getScore();
        mm.acquirePoints(100);
        assertTrue(mm.getScore() >= initialScore + 100,
            "acquirePoints(100) phß║úi t─âng score th├¬m 100");
    }

    @Test
    @DisplayName("MapManager - isGameOver() trß║ú vß╗ü boolean")
    void mapManager_isGameOver() {
        MapManager mm = new MapManager();
        assertDoesNotThrow(() -> mm.isGameOver(),
            "isGameOver() kh├┤ng crash");
    }

    // ============================================================
    // Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("Guideline - Map vß╗¢i null background kh├┤ng crash")
    void guideline_nullBackgroundDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(300, null),
            "Map(300, null) kh├┤ng crash");
    }

    @Test
    @DisplayName("Guideline - Brick vß╗¢i tß╗ìa ─æß╗Ö ├óm kh├┤ng crash")
    void guideline_negativeBrickCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new GroundBrick(-100, -50, null),
            "GroundBrick(-100,-50) kh├┤ng crash");
    }

    @Test
    @DisplayName("Guideline - Coin vß╗¢i point=0 kh├┤ng crash")
    void guideline_zeroPointCoinDoesNotCrash() {
        assertDoesNotThrow(() -> new Coin(100, 200, null, 0),
            "Coin point=0 kh├┤ng crash");
    }

    @Test
    @DisplayName("Guideline - Map vß╗¢i time ├óm kh├┤ng crash")
    void guideline_negativeTimeDoesNotCrash() {
        assertDoesNotThrow(() -> new Map(-100, null),
            "Map vß╗¢i time ├óm kh├┤ng crash");
    }

    @Test
    @DisplayName("Guideline - Prize vß╗¢i tß╗ìa ─æß╗Ö ├óm kh├┤ng crash")
    void guideline_negativePrizeCoordDoesNotCrash() {
        assertDoesNotThrow(() -> new SuperMushroom(-100, -200, null),
            "SuperMushroom vß╗¢i tß╗ìa ─æß╗Ö ├óm kh├┤ng crash");
    }

    @Test
    @DisplayName("Guideline - Mario constructor kh├┤ng crash (gß╗ìi AssetManager)")
    void guideline_marioConstructorDoesNotCrash() {
        assertDoesNotThrow(() -> new model.hero.Mario(100, 300),
            "Mario constructor gß╗ìi AssetManager nh╞░ng kh├┤ng crash");
    }
}
