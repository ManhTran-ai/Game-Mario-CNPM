package test.nguoi4;

import manager.GameConstants;
import manager.SoundManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Release Testing - Ng╞░ß╗¥i 4 kiß╗âm thß╗¡ domain cß╗ºa Ng╞░ß╗¥i 5:
 * GameConstants, ImageLoader, AssetManager, Animation,
 * MapSelection, StartScreenSelection, SoundManager,
 * MapRenderer, HUDRenderer
 *
 * Mß╗Ñc ─æ├¡ch: X├íc nhß║¡n hß╗ç thß╗æng UI, rendering, input, ├óm thanh hoß║ít ─æß╗Öng ─æ├║ng.
 *
 * Ch├║ ├╜:
 *   AssetManager l├á Singleton ΓåÆ getInstance()
 *   HUDRenderer cß║ºn Font, BufferedImage ΓåÆ test bß║▒ng assertDoesNotThrow
 *   SoundManager c├│ playFireFlower(), playOneUp(), playSuperMushroom(),
 *   playFireball(), restartBackground(), setVolume()
 */
public class Nguoi4_ReleaseTest {

    // ============================================================
    // 1. Requirements-based Testing: GameConstants
    // ============================================================

    @Test
    @DisplayName("REQ-GC-001: GRAVITY > 0")
    void reqGc001_gravityPositive() {
        assertTrue(GameConstants.GRAVITY > 0,
            "REQ-GC-001: GRAVITY phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-002: SCREEN_WIDTH > 0")
    void reqGc002_screenWidthPositive() {
        assertTrue(GameConstants.SCREEN_WIDTH > 0,
            "REQ-GC-002: SCREEN_WIDTH phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-003: SCREEN_HEIGHT > 0")
    void reqGc003_screenHeightPositive() {
        assertTrue(GameConstants.SCREEN_HEIGHT > 0,
            "REQ-GC-003: SCREEN_HEIGHT phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-004: SMALL_MARIO_WIDTH > 0")
    void reqGc004_smallMarioWidthPositive() {
        assertTrue(GameConstants.SMALL_MARIO_WIDTH > 0,
            "REQ-GC-004: SMALL_MARIO_WIDTH phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-005: MARIO_JUMP_VELOCITY > 0")
    void reqGc005_jumpVelocityPositive() {
        assertTrue(GameConstants.MARIO_JUMP_VELOCITY > 0,
            "REQ-GC-005: MARIO_JUMP_VELOCITY phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-006: BRICK_SIZE > 0")
    void reqGc006_brickSizePositive() {
        assertTrue(GameConstants.BRICK_SIZE > 0,
            "REQ-GC-006: BRICK_SIZE phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-007: MARIO_MOVE_SPEED > 0")
    void reqGc007_marioMoveSpeedPositive() {
        assertTrue(GameConstants.MARIO_MOVE_SPEED > 0,
            "REQ-GC-007: MARIO_MOVE_SPEED phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-008: ENEMY_MOVE_SPEED > 0")
    void reqGc008_enemyMoveSpeedPositive() {
        assertTrue(GameConstants.ENEMY_MOVE_SPEED > 0,
            "REQ-GC-008: ENEMY_MOVE_SPEED phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-009: FIREBALL_SPEED > 0")
    void reqGc009_fireballSpeedPositive() {
        assertTrue(GameConstants.FIREBALL_SPEED > 0,
            "REQ-GC-009: FIREBALL_SPEED phß║úi > 0");
    }

    @Test
    @DisplayName("REQ-GC-010: SUPER_MARIO_HEIGHT > SMALL_MARIO_HEIGHT")
    void reqGc010_superMarioHeightGreater() {
        assertTrue(GameConstants.SUPER_MARIO_HEIGHT > GameConstants.SMALL_MARIO_HEIGHT,
            "REQ-GC-010: SUPER_MARIO_HEIGHT phß║úi > SMALL_MARIO_HEIGHT");
    }

    // ============================================================
    // 2. Requirements-based Testing: ImageLoader
    // ============================================================

    @Test
    @DisplayName("REQ-IMG-001: ImageLoader constructor tß║ío ─æ╞░ß╗úc instance")
    void reqImg001_imageLoaderInit() {
        ImageLoader loader = new ImageLoader();
        assertNotNull(loader,
            "REQ-IMG-001: ImageLoader phß║úi tß║ío ─æ╞░ß╗úc instance");
    }

    @Test
    @DisplayName("REQ-IMG-002: ImageLoader.getSubImage() trß║ú BufferedImage")
    void reqImg002_getSubImage() {
        ImageLoader loader = new ImageLoader();
        BufferedImage atlas = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);
        BufferedImage sub = loader.getSubImage(atlas, 1, 1, 48, 48);
        assertNotNull(sub,
            "REQ-IMG-002: getSubImage phß║úi trß║ú BufferedImage");
    }

    @Test
    @DisplayName("REQ-IMG-003: ImageLoader.loadImage(File) vß╗¢i null kh├┤ng crash")
    void reqImg003_loadImageFromNullFile() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage((File) null),
            "REQ-IMG-003: loadImage(null File) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-IMG-004: ImageLoader.loadImage(String) vß╗¢i path null kh├┤ng crash")
    void reqImg004_loadImageFromNullPath() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage((String) null),
            "REQ-IMG-004: loadImage(null String) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-IMG-005: ImageLoader.getLeftFrames(int) kh├┤ng crash")
    void reqImg005_getLeftFrames() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.getLeftFrames(0),
            "REQ-IMG-005: getLeftFrames(int) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-IMG-006: ImageLoader.getRightFrames(int) kh├┤ng crash")
    void reqImg006_getRightFrames() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.getRightFrames(0),
            "REQ-IMG-006: getRightFrames(int) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-IMG-007: ImageLoader.getBrickFrames() kh├┤ng crash")
    void reqImg007_getBrickFrames() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.getBrickFrames(),
            "REQ-IMG-007: getBrickFrames() kh├┤ng crash");
    }

    // ============================================================
    // 3. Requirements-based Testing: AssetManager
    // ============================================================

    @Test
    @DisplayName("REQ-AM-001: AssetManager.getInstance() trß║ú instance")
    void reqAm001_assetManagerGetInstance() {
        AssetManager am = AssetManager.getInstance();
        assertNotNull(am,
            "REQ-AM-001: AssetManager.getInstance() phß║úi trß║ú instance");
    }

    @Test
    @DisplayName("REQ-AM-002: AssetManager.getInstance() l├á Singleton")
    void reqAm002_assetManagerIsSingleton() {
        AssetManager am1 = AssetManager.getInstance();
        AssetManager am2 = AssetManager.getInstance();
        assertSame(am1, am2,
            "REQ-AM-002: getInstance() phß║úi trß║ú c├╣ng instance (Singleton)");
    }

    @Test
    @DisplayName("REQ-AM-003: AssetManager.getImageLoader() kh├íc null")
    void reqAm003_getImageLoaderNotNull() {
        AssetManager am = AssetManager.getInstance();
        assertNotNull(am.getImageLoader(),
            "REQ-AM-003: getImageLoader() kh├┤ng ─æ╞░ß╗úc null");
    }

    // ============================================================
    // 4. Requirements-based Testing: Animation
    // ============================================================

    @Test
    @DisplayName("REQ-ANM-001: Animation constructor vß╗¢i null arrays kh├┤ng crash")
    void reqAnm001_animationNullArrays() {
        assertDoesNotThrow(() -> new Animation(null, null),
            "REQ-ANM-001: Animation(null, null) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-ANM-002: Animation.animate() trß║ú BufferedImage")
    void reqAnm002_animateReturnsBufferedImage() {
        BufferedImage[] left = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        BufferedImage[] right = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        Animation anim = new Animation(left, right);

        BufferedImage frame = anim.animate(1, true);
        assertNotNull(frame,
            "REQ-ANM-002: animate() phß║úi trß║ú BufferedImage");
    }

    @Test
    @DisplayName("REQ-ANM-003: Animation.animate(toRight=false) trß║ú BufferedImage")
    void reqAnm003_animateToLeft() {
        BufferedImage[] left = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        BufferedImage[] right = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        Animation anim = new Animation(left, right);

        BufferedImage frame = anim.animate(1, false);
        assertNotNull(frame,
            "REQ-ANM-003: animate(toRight=false) phß║úi trß║ú BufferedImage");
    }

    @Test
    @DisplayName("REQ-ANM-004: Animation.getLeftFrames() v├á getRightFrames()")
    void reqAnm004_getFrames() {
        BufferedImage[] left = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        BufferedImage[] right = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        Animation anim = new Animation(left, right);

        assertNotNull(anim.getLeftFrames(),
            "REQ-ANM-004: getLeftFrames() kh├┤ng ─æ╞░ß╗úc null");
        assertNotNull(anim.getRightFrames(),
            "REQ-ANM-004: getRightFrames() kh├┤ng ─æ╞░ß╗úc null");
    }

    // ============================================================
    // 5. Requirements-based Testing: MapSelection
    // ============================================================

    @Test
    @DisplayName("REQ-MS-001: MapSelection constructor tß║ío ─æ╞░ß╗úc instance")
    void reqMs001_mapSelectionInit() {
        MapSelection selection = new MapSelection();
        assertNotNull(selection,
            "REQ-MS-001: MapSelection phß║úi tß║ío ─æ╞░ß╗úc instance");
    }

    @Test
    @DisplayName("REQ-MS-002: MapSelection.draw() vß╗¢i null Graphics kh├┤ng crash")
    void reqMs002_drawWithNullGraphics() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> selection.draw(null),
            "REQ-MS-002: draw(null) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-MS-003: MapSelection.selectMap(Point) vß╗¢i null trß║ú null")
    void reqMs003_selectMapNullPoint() {
        MapSelection selection = new MapSelection();
        String result = selection.selectMap((Point) null);
        assertNull(result,
            "REQ-MS-003: selectMap(null) trß║ú null");
    }

    @Test
    @DisplayName("REQ-MS-004: MapSelection.changeSelectedMap vß╗¢i up=true")
    void reqMs004_changeSelectedMapUp() {
        MapSelection selection = new MapSelection();
        int result = selection.changeSelectedMap(0, true);
        assertTrue(result >= 0,
            "REQ-MS-004: changeSelectedMap(up=true) phß║úi trß║ú index >= 0");
    }

    @Test
    @DisplayName("REQ-MS-005: MapSelection.changeSelectedMap vß╗¢i up=false")
    void reqMs005_changeSelectedMapDown() {
        MapSelection selection = new MapSelection();
        int result = selection.changeSelectedMap(0, false);
        assertTrue(result >= 0,
            "REQ-MS-005: changeSelectedMap(up=false) phß║úi trß║ú index >= 0");
    }

    @Test
    @DisplayName("REQ-MS-006: MapSelection.selectMap(int) vß╗¢i index hß╗úp lß╗ç")
    void reqMs006_selectMapWithValidIndex() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> selection.selectMap(0),
            "REQ-MS-006: selectMap(0) kh├┤ng crash");
    }

    // ============================================================
    // 6. Requirements-based Testing: StartScreenSelection
    // ============================================================

    @Test
    @DisplayName("REQ-SS-001: StartScreenSelection.START_GAME tß╗ôn tß║íi")
    void reqSs001_startGameExists() {
        assertNotNull(StartScreenSelection.START_GAME,
            "REQ-SS-001: START_GAME enum phß║úi tß╗ôn tß║íi");
    }

    @Test
    @DisplayName("REQ-SS-002: StartScreenSelection.VIEW_HELP tß╗ôn tß║íi")
    void reqSs002_viewHelpExists() {
        assertNotNull(StartScreenSelection.VIEW_HELP,
            "REQ-SS-002: VIEW_HELP enum phß║úi tß╗ôn tß║íi");
    }

    @Test
    @DisplayName("REQ-SS-003: StartScreenSelection.VIEW_ABOUT tß╗ôn tß║íi")
    void reqSs003_viewAboutExists() {
        assertNotNull(StartScreenSelection.VIEW_ABOUT,
            "REQ-SS-003: VIEW_ABOUT enum phß║úi tß╗ôn tß║íi");
    }

    @Test
    @DisplayName("REQ-SS-004: getLineNumber() trß║ú vß╗ü ─æ├║ng gi├í trß╗ï")
    void reqSs004_getLineNumber() {
        assertEquals(0, StartScreenSelection.START_GAME.getLineNumber(),
            "REQ-SS-004: START_GAME.getLineNumber() phß║úi bß║▒ng 0");
        assertEquals(1, StartScreenSelection.VIEW_HELP.getLineNumber(),
            "REQ-SS-004: VIEW_HELP.getLineNumber() phß║úi bß║▒ng 1");
        assertEquals(2, StartScreenSelection.VIEW_ABOUT.getLineNumber(),
            "REQ-SS-004: VIEW_ABOUT.getLineNumber() phß║úi bß║▒ng 2");
    }

    @Test
    @DisplayName("REQ-SS-005: select(true) di chuyß╗ân l├¬n")
    void reqSs005_selectUp() {
        StartScreenSelection result = StartScreenSelection.START_GAME.select(true);
        assertNotNull(result,
            "REQ-SS-005: select(true) tß╗½ START_GAME phß║úi trß║ú gi├í trß╗ï");
    }

    @Test
    @DisplayName("REQ-SS-006: select(false) di chuyß╗ân xuß╗æng")
    void reqSs006_selectDown() {
        StartScreenSelection result = StartScreenSelection.START_GAME.select(false);
        assertNotNull(result,
            "REQ-SS-006: select(false) tß╗½ START_GAME phß║úi trß║ú gi├í trß╗ï");
    }

    @Test
    @DisplayName("REQ-SS-007: getSelection(int) trß║ú gi├í trß╗ï hß╗úp lß╗ç")
    void reqSs007_getSelection() {
        assertDoesNotThrow(() -> StartScreenSelection.getSelection(0),
            "REQ-SS-007: getSelection(0) kh├┤ng crash");
    }

    // ============================================================
    // 7. Requirements-based Testing: SoundManager
    // ============================================================

    @Test
    @DisplayName("REQ-SM-001: SoundManager constructor tß║ío ─æ╞░ß╗úc instance")
    void reqSm001_soundManagerInit() {
        SoundManager sm = new SoundManager();
        assertNotNull(sm,
            "REQ-SM-001: SoundManager phß║úi tß║ío ─æ╞░ß╗úc instance");
    }

    @Test
    @DisplayName("REQ-SM-002: SoundManager.playJump() kh├┤ng crash")
    void reqSm002_playJump() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playJump(),
            "REQ-SM-002: playJump() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-003: SoundManager.playCoin() kh├┤ng crash")
    void reqSm003_playCoin() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playCoin(),
            "REQ-SM-003: playCoin() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-004: SoundManager.playStomp() kh├┤ng crash")
    void reqSm004_playStomp() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playStomp(),
            "REQ-SM-004: playStomp() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-005: SoundManager.playMarioDies() kh├┤ng crash")
    void reqSm005_playMarioDies() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playMarioDies(),
            "REQ-SM-005: playMarioDies() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-006: SoundManager.playGameOver() kh├┤ng crash")
    void reqSm006_playGameOver() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playGameOver(),
            "REQ-SM-006: playGameOver() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-007: SoundManager.pauseBackground() kh├┤ng crash")
    void reqSm007_pauseBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.pauseBackground(),
            "REQ-SM-007: pauseBackground() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-008: SoundManager.resumeBackground() kh├┤ng crash")
    void reqSm008_resumeBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.resumeBackground(),
            "REQ-SM-008: resumeBackground() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-009: SoundManager.cleanupFinishedClips() kh├┤ng crash")
    void reqSm009_cleanupFinishedClips() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.cleanupFinishedClips(),
            "REQ-SM-009: cleanupFinishedClips() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-010: SoundManager.playFireFlower() kh├┤ng crash")
    void reqSm010_playFireFlower() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playFireFlower(),
            "REQ-SM-010: playFireFlower() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-011: SoundManager.playOneUp() kh├┤ng crash")
    void reqSm011_playOneUp() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playOneUp(),
            "REQ-SM-011: playOneUp() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-012: SoundManager.playSuperMushroom() kh├┤ng crash")
    void reqSm012_playSuperMushroom() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playSuperMushroom(),
            "REQ-SM-012: playSuperMushroom() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-013: SoundManager.playFireball() kh├┤ng crash")
    void reqSm013_playFireball() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playFireball(),
            "REQ-SM-013: playFireball() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-014: SoundManager.restartBackground() kh├┤ng crash")
    void reqSm014_restartBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.restartBackground(),
            "REQ-SM-014: restartBackground() kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-015: SoundManager.setVolume(float) kh├┤ng crash")
    void reqSm015_setVolume() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.setVolume(0.0f),
            "REQ-SM-015: setVolume(0.0f) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-SM-016: SoundManager.setVolume vß╗¢i gi├í trß╗ï bi├¬n kh├┤ng crash")
    void reqSm016_setVolumeBoundary() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.setVolume(GameConstants.MAX_VOLUME),
            "REQ-SM-016: setVolume(MAX_VOLUME) kh├┤ng crash");
        assertDoesNotThrow(() -> sm.setVolume(GameConstants.MIN_VOLUME),
            "REQ-SM-016: setVolume(MIN_VOLUME) kh├┤ng crash");
    }

    // ============================================================
    // 8. Requirements-based Testing: MapRenderer
    // ============================================================

    @Test
    @DisplayName("REQ-MR-001: MapRenderer constructor tß║ío ─æ╞░ß╗úc instance")
    void reqMr001_mapRendererInit() {
        MapRenderer renderer = new MapRenderer();
        assertNotNull(renderer,
            "REQ-MR-001: MapRenderer phß║úi tß║ío ─æ╞░ß╗úc instance");
    }

    @Test
    @DisplayName("REQ-MR-002: MapRenderer.render(null, null, null) kh├┤ng crash")
    void reqMr002_renderWithNull() {
        MapRenderer renderer = new MapRenderer();
        assertDoesNotThrow(() -> renderer.render(null, null, null),
            "REQ-MR-002: render(null, null, null) kh├┤ng crash");
    }

    // ============================================================
    // 9. Requirements-based Testing: HUDRenderer
    // ============================================================

    @Test
    @DisplayName("REQ-HUD-001: HUDRenderer constructor tß║ío ─æ╞░ß╗úc instance")
    void reqHud001_hudRendererInit() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertNotNull(renderer,
            "REQ-HUD-001: HUDRenderer phß║úi tß║ío ─æ╞░ß╗úc instance");
    }

    @Test
    @DisplayName("REQ-HUD-002: HUDRenderer.render(null, ...) kh├┤ng crash")
    void reqHud002_renderWithNullGraphics() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.render(null, 0, 3, 0, 300),
            "REQ-HUD-002: render(null, ...) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-HUD-003: HUDRenderer.drawVictoryScreen(null, ...) kh├┤ng crash")
    void reqHud003_drawVictoryScreenNull() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.drawVictoryScreen(null, 0, 0),
            "REQ-HUD-003: drawVictoryScreen(null, ...) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-HUD-004: HUDRenderer.drawPauseScreen(null) kh├┤ng crash")
    void reqHud004_drawPauseScreenNull() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.drawPauseScreen(null),
            "REQ-HUD-004: drawPauseScreen(null) kh├┤ng crash");
    }

    @Test
    @DisplayName("REQ-HUD-005: HUDRenderer.drawGameOverScreen(null, ...) kh├┤ng crash")
    void reqHud005_drawGameOverScreenNull() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.drawGameOverScreen(null, 0, 0),
            "REQ-HUD-005: drawGameOverScreen(null, ...) kh├┤ng crash");
    }

    // ============================================================
    // 10. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("SCN-001: MapSelection chß╗ìn map vß╗¢i index hß╗úp lß╗ç")
    void scn001_selectMapWithValidIndex() {
        MapSelection selection = new MapSelection();
        String result = selection.selectMap(0);
        assertNotNull(result,
            "SCN-001: selectMap(0) vß╗¢i index hß╗úp lß╗ç phß║úi trß║ú t├¬n map");
    }

    @Test
    @DisplayName("SCN-002: StartScreenSelection navigation wrap-around")
    void scn002_startScreenSelectionWrapAround() {
        StartScreenSelection result = StartScreenSelection.VIEW_ABOUT.select(false);
        assertEquals(StartScreenSelection.START_GAME, result,
            "SCN-002: Tß╗½ VIEW_ABOUT select(false) phß║úi wrap vß╗ü START_GAME");
    }

    @Test
    @DisplayName("SCN-003: SoundManager ph├ít nhiß╗üu ├óm thanh li├¬n tiß║┐p")
    void scn003_multipleSounds() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            sm.playJump();
            sm.playCoin();
            sm.playStomp();
        }, "SCN-003: Ph├ít nhiß╗üu ├óm thanh li├¬n tiß║┐p kh├┤ng crash");
    }

    @Test
    @DisplayName("SCN-004: Animation ho├ín ─æß╗òi h╞░ß╗¢ng li├¬n tß╗Ñc")
    void scn004_animationDirectionChange() {
        BufferedImage[] left = new BufferedImage[3];
        BufferedImage[] right = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            left[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
            right[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        Animation anim = new Animation(left, right);

        assertDoesNotThrow(() -> {
            anim.animate(1, true);
            anim.animate(1, false);
            anim.animate(1, true);
            anim.animate(1, false);
        }, "SCN-004: Ho├ín ─æß╗òi h╞░ß╗¢ng li├¬n tß╗Ñc kh├┤ng crash");
    }

    @Test
    @DisplayName("SCN-005: MapSelection thay ─æß╗òi selection nhiß╗üu lß║ºn")
    void scn005_mapSelectionMultipleChanges() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> {
            selection.changeSelectedMap(0, true);
            selection.changeSelectedMap(0, false);
            selection.changeSelectedMap(0, true);
            selection.changeSelectedMap(0, false);
        }, "SCN-005: Thay ─æß╗òi selection nhiß╗üu lß║ºn kh├┤ng crash");
    }

    @Test
    @DisplayName("SCN-006: StartScreenSelection ─æiß╗üu h╞░ß╗¢ng ─æß║ºy ─æß╗º")
    void scn006_startScreenSelectionFullNavigation() {
        StartScreenSelection current = StartScreenSelection.START_GAME;
        for (int i = 0; i < 5; i++) {
            current = current.select(false);
        }
        assertNotNull(current,
            "SCN-006: Navigation nhiß╗üu lß║ºn vß║½n kh├┤ng null");
    }

    @Test
    @DisplayName("SCN-007: SoundManager setVolume nhiß╗üu lß║ºn")
    void scn007_multipleVolumeChanges() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            sm.setVolume(-40.0f);
            sm.setVolume(-20.0f);
            sm.setVolume(0.0f);
            sm.setVolume(6.0f);
        }, "SCN-007: setVolume nhiß╗üu lß║ºn kh├┤ng crash");
    }

    // ============================================================
    // 11. Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("GUIDE-001: SoundManager ph├ít ├óm thanh khi ch╞░a pause")
    void guide001_playBeforePauseDoesNotCrash() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playJump(),
            "GUIDE-001: playJump() khi ch╞░a pause kh├┤ng crash");
    }

    @Test
    @DisplayName("GUIDE-002: MapRenderer vß╗¢i null map kh├┤ng crash")
    void guide002_mapRendererNullMap() {
        MapRenderer renderer = new MapRenderer();
        assertDoesNotThrow(() -> renderer.render(null, null, null),
            "GUIDE-002: render(null) kh├┤ng crash");
    }

    @Test
    @DisplayName("GUIDE-003: ImageLoader.loadImage vß╗¢i path kh├┤ng tß╗ôn tß║íi kh├┤ng crash")
    void guide003_loadImageInvalidPath() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage("/nonexistent.png"),
            "GUIDE-003: loadImage(path kh├┤ng tß╗ôn tß║íi) kh├┤ng crash");
    }

    @Test
    @DisplayName("GUIDE-004: Animation vß╗¢i mß║úng rß╗ùng kh├┤ng crash")
    void guide004_animationEmptyArrays() {
        assertDoesNotThrow(() -> {
            Animation anim = new Animation(new BufferedImage[0], new BufferedImage[0]);
            anim.animate(1, true);
        }, "GUIDE-004: Animation vß╗¢i mß║úng rß╗ùng kh├┤ng crash");
    }

    @Test
    @DisplayName("GUIDE-005: HUDRenderer vß╗¢i Graphics null kh├┤ng crash")
    void guide005_hudRendererNullGraphics() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> {
            renderer.render(null, 0, 0, 0, 0);
            renderer.drawVictoryScreen(null, 0, 0);
            renderer.drawPauseScreen(null);
            renderer.drawGameOverScreen(null, 0, 0);
        }, "GUIDE-005: Tß║Ñt cß║ú method vß╗¢i null Graphics kh├┤ng crash");
    }

    @Test
    @DisplayName("GUIDE-006: SoundManager ph├ít sound khi background null")
    void guide006_playSoundWhenBackgroundNull() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            sm.pauseBackground();
            sm.playJump();
            sm.playCoin();
        }, "GUIDE-006: Ph├ít sound sau pause kh├┤ng crash");
    }

    @Test
    @DisplayName("GUIDE-007: AssetManager gß╗ìi nhiß╗üu lß║ºn vß║½n Singleton")
    void guide007_multipleGetInstanceCalls() {
        AssetManager am1 = AssetManager.getInstance();
        AssetManager am2 = AssetManager.getInstance();
        AssetManager am3 = AssetManager.getInstance();
        assertSame(am1, am2);
        assertSame(am2, am3);
    }

    // ============================================================
    // 12. Performance Testing
    // ============================================================

    @Test
    @DisplayName("PERF-001: Animation.animate 1000 lß║ºn trong 500ms")
    void perf001_animationAnimate1000Times() {
        BufferedImage[] left = new BufferedImage[5];
        BufferedImage[] right = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            left[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
            right[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        Animation anim = new Animation(left, right);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            anim.animate(1, true);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-001: animate 1000 lß║ºn phß║úi < 500ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-002: ImageLoader.getSubImage 1000 lß║ºn trong 500ms")
    void perf002_getSubImage1000Times() {
        ImageLoader loader = new ImageLoader();
        BufferedImage atlas = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            loader.getSubImage(atlas, 1, 1, 48, 48);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-002: getSubImage 1000 lß║ºn phß║úi < 500ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-003: SoundManager.playJump 100 lß║ºn trong 500ms")
    void perf003_playJump100Times() {
        SoundManager sm = new SoundManager();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            sm.playJump();
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-003: playJump 100 lß║ºn phß║úi < 500ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-004: MapSelection.changeSelectedMap 1000 lß║ºn trong 500ms")
    void perf004_changeSelectedMap1000Times() {
        MapSelection selection = new MapSelection();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            selection.changeSelectedMap(0, true);
            selection.changeSelectedMap(0, false);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-004: changeSelectedMap 1000 lß║ºn phß║úi < 500ms. Actual: " + elapsed + "ms");
    }
}
