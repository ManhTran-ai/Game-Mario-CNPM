package test.ngan;

import manager.GameConstants;
import manager.SoundManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Development Testing - Người 5 tự kiểm thử code của mình:
 * GameConstants, ImageLoader, AssetManager, Animation,
 * MapSelection, StartScreenSelection, SoundManager
 *
 * Phạm vi:
 * - Unit test cho từng class
 * - Partition testing (giá trị biên)
 * - Guideline-based testing (null, edge)
 */
public class DevelopmentTest {

    // ============================================================
    // 1. GameConstants Tests
    // ============================================================

    @Test
    @DisplayName("GC-001: GameConstants - GRAVITY > 0")
    void gameConstants_gravityPositive() {
        assertTrue(GameConstants.GRAVITY > 0, "GRAVITY must be > 0");
    }

    @Test
    @DisplayName("GC-002: GameConstants - SCREEN_WIDTH > 0")
    void gameConstants_screenWidthPositive() {
        assertTrue(GameConstants.SCREEN_WIDTH > 0, "SCREEN_WIDTH must be > 0");
    }

    @Test
    @DisplayName("GC-003: GameConstants - SCREEN_HEIGHT > 0")
    void gameConstants_screenHeightPositive() {
        assertTrue(GameConstants.SCREEN_HEIGHT > 0, "SCREEN_HEIGHT must be > 0");
    }

    @Test
    @DisplayName("GC-004: GameConstants - MARIO_JUMP_VELOCITY > 0")
    void gameConstants_jumpVelocityPositive() {
        assertTrue(GameConstants.MARIO_JUMP_VELOCITY > 0, "MARIO_JUMP_VELOCITY must be > 0");
    }

    @Test
    @DisplayName("GC-005: GameConstants - SMALL_MARIO_WIDTH > 0")
    void gameConstants_smallMarioWidthPositive() {
        assertTrue(GameConstants.SMALL_MARIO_WIDTH > 0, "SMALL_MARIO_WIDTH must be > 0");
    }

    @Test
    @DisplayName("GC-006: GameConstants - BRICK_SIZE > 0")
    void gameConstants_brickSizePositive() {
        assertTrue(GameConstants.BRICK_SIZE > 0, "BRICK_SIZE must be > 0");
    }

    // ============================================================
    // 2. ImageLoader Tests
    // ============================================================

    @Test
    @DisplayName("IL-001: ImageLoader - constructor creates instance")
    void imageLoader_init() {
        ImageLoader loader = new ImageLoader();
        assertNotNull(loader, "ImageLoader must create instance");
    }

    @Test
    @DisplayName("IL-002: ImageLoader - getSubImage returns BufferedImage")
    void imageLoader_getSubImage() {
        ImageLoader loader = new ImageLoader();
        BufferedImage atlas = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);
        BufferedImage sub = loader.getSubImage(atlas, 1, 1, 48, 48);
        assertNotNull(sub, "getSubImage must return BufferedImage");
    }

    @Test
    @DisplayName("IL-003: ImageLoader - loadImage(File null) doesn't crash")
    void imageLoader_loadNullFile() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage((File) null),
                "loadImage(null File) must not crash");
    }

    // ============================================================
    // 3. AssetManager Tests
    // ============================================================

    @Test
    @DisplayName("AM-001: AssetManager - getInstance() returns instance")
    void assetManager_getInstance() {
        AssetManager am = AssetManager.getInstance();
        assertNotNull(am, "getInstance() must return instance");
    }

    @Test
    @DisplayName("AM-002: AssetManager - Singleton (same instance twice)")
    void assetManager_singleton() {
        AssetManager am1 = AssetManager.getInstance();
        AssetManager am2 = AssetManager.getInstance();
        assertSame(am1, am2, "getInstance() must return same instance");
    }

    @Test
    @DisplayName("AM-003: AssetManager - getImageLoader() not null")
    void assetManager_getImageLoader() {
        AssetManager am = AssetManager.getInstance();
        assertNotNull(am.getImageLoader(), "getImageLoader() must not be null");
    }

    // ============================================================
    // 4. Animation Tests
    // ============================================================

    @Test
    @DisplayName("AN-001: Animation - constructor with null arrays doesn't crash")
    void animation_nullArrays() {
        assertDoesNotThrow(() -> new Animation(null, null),
                "Animation(null, null) must not crash");
    }

    @Test
    @DisplayName("AN-002: Animation - animate() returns BufferedImage")
    void animation_animate() {
        BufferedImage[] left = new BufferedImage[5];
        BufferedImage[] right = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            left[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
            right[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        Animation anim = new Animation(left, right);
        assertNotNull(anim.animate(1, true), "animate() must return BufferedImage");
    }

    @Test
    @DisplayName("AN-003: Animation - getLeftFrames() works")
    void animation_getLeftFrames() {
        BufferedImage[] left = new BufferedImage[5];
        BufferedImage[] right = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            left[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
            right[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        Animation anim = new Animation(left, right);
        assertNotNull(anim.getLeftFrames(), "getLeftFrames() must not be null");
    }

    @Test
    @DisplayName("AN-004: Animation - getRightFrames() works")
    void animation_getRightFrames() {
        BufferedImage[] left = new BufferedImage[5];
        BufferedImage[] right = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            left[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
            right[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        Animation anim = new Animation(left, right);
        assertNotNull(anim.getRightFrames(), "getRightFrames() must not be null");
    }

    // ============================================================
    // 5. MapSelection Tests
    // ============================================================

    @Test
    @DisplayName("MS-001: MapSelection - constructor creates instance")
    void mapSelection_init() {
        MapSelection selection = new MapSelection();
        assertNotNull(selection, "MapSelection must create instance");
    }

    @Test
    @DisplayName("MS-002: MapSelection - draw(null) doesn't crash")
    void mapSelection_drawNull() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> selection.draw(null),
                "draw(null) must not crash");
    }

    @Test
    @DisplayName("MS-003: MapSelection - selectMap(Point null) returns null")
    void mapSelection_selectMapNullPoint() {
        MapSelection selection = new MapSelection();
        assertNull(selection.selectMap((Point) null),
                "selectMap(null) must return null");
    }

    @Test
    @DisplayName("MS-004: MapSelection - changeSelectedMap up works")
    void mapSelection_changeSelectedMapUp() {
        MapSelection selection = new MapSelection();
        int result = selection.changeSelectedMap(0, true);
        assertTrue(result >= 0, "changeSelectedMap(up=true) must return >= 0");
    }

    @Test
    @DisplayName("MS-005: MapSelection - changeSelectedMap down works")
    void mapSelection_changeSelectedMapDown() {
        MapSelection selection = new MapSelection();
        int result = selection.changeSelectedMap(0, false);
        assertTrue(result >= 0, "changeSelectedMap(up=false) must return >= 0");
    }

    // ============================================================
    // 6. StartScreenSelection Tests
    // ============================================================

    @Test
    @DisplayName("SS-001: StartScreenSelection - START_GAME exists")
    void startScreenSelection_startGameExists() {
        assertNotNull(StartScreenSelection.START_GAME,
                "START_GAME must exist");
    }

    @Test
    @DisplayName("SS-002: StartScreenSelection - VIEW_HELP exists")
    void startScreenSelection_viewHelpExists() {
        assertNotNull(StartScreenSelection.VIEW_HELP,
                "VIEW_HELP must exist");
    }

    @Test
    @DisplayName("SS-003: StartScreenSelection - VIEW_ABOUT exists")
    void startScreenSelection_viewAboutExists() {
        assertNotNull(StartScreenSelection.VIEW_ABOUT,
                "VIEW_ABOUT must exist");
    }

    @Test
    @DisplayName("SS-004: StartScreenSelection - getLineNumber returns correct values")
    void startScreenSelection_getLineNumber() {
        assertEquals(0, StartScreenSelection.START_GAME.getLineNumber(),
                "START_GAME.getLineNumber() must be 0");
        assertEquals(1, StartScreenSelection.VIEW_HELP.getLineNumber(),
                "VIEW_HELP.getLineNumber() must be 1");
        assertEquals(2, StartScreenSelection.VIEW_ABOUT.getLineNumber(),
                "VIEW_ABOUT.getLineNumber() must be 2");
    }

    @Test
    @DisplayName("SS-005: StartScreenSelection - select(true) doesn't crash")
    void startScreenSelection_selectUp() {
        assertDoesNotThrow(() -> StartScreenSelection.START_GAME.select(true),
                "select(true) must not crash");
    }

    @Test
    @DisplayName("SS-006: StartScreenSelection - select(false) doesn't crash")
    void startScreenSelection_selectDown() {
        assertDoesNotThrow(() -> StartScreenSelection.VIEW_ABOUT.select(false),
                "select(false) must not crash");
    }

    // ============================================================
    // 7. SoundManager Tests
    // ============================================================

    @Test
    @DisplayName("SM-001: SoundManager - constructor creates instance")
    void soundManager_init() {
        SoundManager sm = new SoundManager();
        assertNotNull(sm, "SoundManager must create instance");
    }

    @Test
    @DisplayName("SM-002: SoundManager - playJump() doesn't crash")
    void soundManager_playJump() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playJump(),
                "playJump() must not crash");
    }

    @Test
    @DisplayName("SM-003: SoundManager - playCoin() doesn't crash")
    void soundManager_playCoin() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playCoin(),
                "playCoin() must not crash");
    }

    @Test
    @DisplayName("SM-004: SoundManager - playStomp() doesn't crash")
    void soundManager_playStomp() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playStomp(),
                "playStomp() must not crash");
    }

    @Test
    @DisplayName("SM-005: SoundManager - playMarioDies() doesn't crash")
    void soundManager_playMarioDies() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playMarioDies(),
                "playMarioDies() must not crash");
    }

    @Test
    @DisplayName("SM-006: SoundManager - playGameOver() doesn't crash")
    void soundManager_playGameOver() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playGameOver(),
                "playGameOver() must not crash");
    }

    @Test
    @DisplayName("SM-007: SoundManager - playFireFlower() doesn't crash")
    void soundManager_playFireFlower() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playFireFlower(),
                "playFireFlower() must not crash");
    }

    @Test
    @DisplayName("SM-008: SoundManager - playOneUp() doesn't crash")
    void soundManager_playOneUp() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playOneUp(),
                "playOneUp() must not crash");
    }

    @Test
    @DisplayName("SM-009: SoundManager - playSuperMushroom() doesn't crash")
    void soundManager_playSuperMushroom() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playSuperMushroom(),
                "playSuperMushroom() must not crash");
    }

    @Test
    @DisplayName("SM-010: SoundManager - playFireball() doesn't crash")
    void soundManager_playFireball() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playFireball(),
                "playFireball() must not crash");
    }

    @Test
    @DisplayName("SM-011: SoundManager - pauseBackground() doesn't crash")
    void soundManager_pauseBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.pauseBackground(),
                "pauseBackground() must not crash");
    }

    @Test
    @DisplayName("SM-012: SoundManager - resumeBackground() doesn't crash")
    void soundManager_resumeBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.resumeBackground(),
                "resumeBackground() must not crash");
    }

    @Test
    @DisplayName("SM-013: SoundManager - restartBackground() doesn't crash")
    void soundManager_restartBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.restartBackground(),
                "restartBackground() must not crash");
    }

    @Test
    @DisplayName("SM-014: SoundManager - cleanupFinishedClips() doesn't crash")
    void soundManager_cleanupFinishedClips() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.cleanupFinishedClips(),
                "cleanupFinishedClips() must not crash");
    }

    @Test
    @DisplayName("SM-015: SoundManager - setVolume() doesn't crash")
    void soundManager_setVolume() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.setVolume(GameConstants.DEFAULT_VOLUME),
                "setVolume() must not crash");
    }

    // ============================================================
    // 8. Guideline-based Testing (Edge Cases & Robustness)
    // ============================================================

    @Test
    @DisplayName("GL-001: SoundManager - playing sound multiple times doesn't crash")
    void guideline_soundManagerMultiplePlays() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10; i++) sm.playJump();
            for (int i = 0; i < 10; i++) sm.playCoin();
            for (int i = 0; i < 10; i++) sm.playStomp();
        }, "Playing sound multiple times must not crash");
    }

    @Test
    @DisplayName("GL-002: Animation - animate() with null arrays doesn't crash")
    void guideline_animationWithNullArrays() {
        Animation anim = new Animation(null, null);
        assertDoesNotThrow(() -> anim.animate(1, true),
                "animate() with null arrays must not crash");
    }

    @Test
    @DisplayName("GL-003: ImageLoader - loadImage with invalid path doesn't crash")
    void guideline_loadInvalidPath() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage("/nonexistent/path/to/image.png"),
                "loadImage with invalid path must not crash");
    }
}
