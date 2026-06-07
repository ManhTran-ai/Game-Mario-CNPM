package test.test.nguoi4;

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
 * Release Testing - Người 4 kiểm thử domain của Người 5:
 * GameConstants, ImageLoader, AssetManager, Animation,
 * MapSelection, StartScreenSelection, SoundManager,
 * MapRenderer, HUDRenderer
 *
 * Mục đích: Xác nhận hệ thống UI, rendering, input, âm thanh hoạt động đúng.
 *
 * Chú ý:
 *   AssetManager là Singleton → getInstance()
 *   HUDRenderer cần Font, BufferedImage → test bằng assertDoesNotThrow
 *   SoundManager có playFireFlower(), playOneUp(), playSuperMushroom(),
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
            "REQ-GC-001: GRAVITY phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-002: SCREEN_WIDTH > 0")
    void reqGc002_screenWidthPositive() {
        assertTrue(GameConstants.SCREEN_WIDTH > 0,
            "REQ-GC-002: SCREEN_WIDTH phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-003: SCREEN_HEIGHT > 0")
    void reqGc003_screenHeightPositive() {
        assertTrue(GameConstants.SCREEN_HEIGHT > 0,
            "REQ-GC-003: SCREEN_HEIGHT phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-004: SMALL_MARIO_WIDTH > 0")
    void reqGc004_smallMarioWidthPositive() {
        assertTrue(GameConstants.SMALL_MARIO_WIDTH > 0,
            "REQ-GC-004: SMALL_MARIO_WIDTH phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-005: MARIO_JUMP_VELOCITY > 0")
    void reqGc005_jumpVelocityPositive() {
        assertTrue(GameConstants.MARIO_JUMP_VELOCITY > 0,
            "REQ-GC-005: MARIO_JUMP_VELOCITY phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-006: BRICK_SIZE > 0")
    void reqGc006_brickSizePositive() {
        assertTrue(GameConstants.BRICK_SIZE > 0,
            "REQ-GC-006: BRICK_SIZE phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-007: MARIO_MOVE_SPEED > 0")
    void reqGc007_marioMoveSpeedPositive() {
        assertTrue(GameConstants.MARIO_MOVE_SPEED > 0,
            "REQ-GC-007: MARIO_MOVE_SPEED phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-008: ENEMY_MOVE_SPEED > 0")
    void reqGc008_enemyMoveSpeedPositive() {
        assertTrue(GameConstants.ENEMY_MOVE_SPEED > 0,
            "REQ-GC-008: ENEMY_MOVE_SPEED phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-009: FIREBALL_SPEED > 0")
    void reqGc009_fireballSpeedPositive() {
        assertTrue(GameConstants.FIREBALL_SPEED > 0,
            "REQ-GC-009: FIREBALL_SPEED phải > 0");
    }

    @Test
    @DisplayName("REQ-GC-010: SUPER_MARIO_HEIGHT > SMALL_MARIO_HEIGHT")
    void reqGc010_superMarioHeightGreater() {
        assertTrue(GameConstants.SUPER_MARIO_HEIGHT > GameConstants.SMALL_MARIO_HEIGHT,
            "REQ-GC-010: SUPER_MARIO_HEIGHT phải > SMALL_MARIO_HEIGHT");
    }

    // ============================================================
    // 2. Requirements-based Testing: ImageLoader
    // ============================================================

    @Test
    @DisplayName("REQ-IMG-001: ImageLoader constructor tạo được instance")
    void reqImg001_imageLoaderInit() {
        ImageLoader loader = new ImageLoader();
        assertNotNull(loader,
            "REQ-IMG-001: ImageLoader phải tạo được instance");
    }

    @Test
    @DisplayName("REQ-IMG-002: ImageLoader.getSubImage() trả BufferedImage")
    void reqImg002_getSubImage() {
        ImageLoader loader = new ImageLoader();
        BufferedImage atlas = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);
        BufferedImage sub = loader.getSubImage(atlas, 1, 1, 48, 48);
        assertNotNull(sub,
            "REQ-IMG-002: getSubImage phải trả BufferedImage");
    }

    @Test
    @DisplayName("REQ-IMG-003: ImageLoader.loadImage(File) với null không crash")
    void reqImg003_loadImageFromNullFile() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage((File) null),
            "REQ-IMG-003: loadImage(null File) không crash");
    }

    @Test
    @DisplayName("REQ-IMG-004: ImageLoader.loadImage(String) với path null không crash")
    void reqImg004_loadImageFromNullPath() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage((String) null),
            "REQ-IMG-004: loadImage(null String) không crash");
    }

    @Test
    @DisplayName("REQ-IMG-005: ImageLoader.getLeftFrames(int) không crash")
    void reqImg005_getLeftFrames() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.getLeftFrames(0),
            "REQ-IMG-005: getLeftFrames(int) không crash");
    }

    @Test
    @DisplayName("REQ-IMG-006: ImageLoader.getRightFrames(int) không crash")
    void reqImg006_getRightFrames() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.getRightFrames(0),
            "REQ-IMG-006: getRightFrames(int) không crash");
    }

    @Test
    @DisplayName("REQ-IMG-007: ImageLoader.getBrickFrames() không crash")
    void reqImg007_getBrickFrames() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.getBrickFrames(),
            "REQ-IMG-007: getBrickFrames() không crash");
    }

    // ============================================================
    // 3. Requirements-based Testing: AssetManager
    // ============================================================

    @Test
    @DisplayName("REQ-AM-001: AssetManager.getInstance() trả instance")
    void reqAm001_assetManagerGetInstance() {
        AssetManager am = AssetManager.getInstance();
        assertNotNull(am,
            "REQ-AM-001: AssetManager.getInstance() phải trả instance");
    }

    @Test
    @DisplayName("REQ-AM-002: AssetManager.getInstance() là Singleton")
    void reqAm002_assetManagerIsSingleton() {
        AssetManager am1 = AssetManager.getInstance();
        AssetManager am2 = AssetManager.getInstance();
        assertSame(am1, am2,
            "REQ-AM-002: getInstance() phải trả cùng instance (Singleton)");
    }

    @Test
    @DisplayName("REQ-AM-003: AssetManager.getImageLoader() khác null")
    void reqAm003_getImageLoaderNotNull() {
        AssetManager am = AssetManager.getInstance();
        assertNotNull(am.getImageLoader(),
            "REQ-AM-003: getImageLoader() không được null");
    }

    // ============================================================
    // 4. Requirements-based Testing: Animation
    // ============================================================

    @Test
    @DisplayName("REQ-ANM-001: Animation constructor với null arrays không crash")
    void reqAnm001_animationNullArrays() {
        assertDoesNotThrow(() -> new Animation(null, null),
            "REQ-ANM-001: Animation(null, null) không crash");
    }

    @Test
    @DisplayName("REQ-ANM-002: Animation.animate() trả BufferedImage")
    void reqAnm002_animateReturnsBufferedImage() {
        BufferedImage[] left = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        BufferedImage[] right = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        Animation anim = new Animation(left, right);

        BufferedImage frame = anim.animate(1, true);
        assertNotNull(frame,
            "REQ-ANM-002: animate() phải trả BufferedImage");
    }

    @Test
    @DisplayName("REQ-ANM-003: Animation.animate(toRight=false) trả BufferedImage")
    void reqAnm003_animateToLeft() {
        BufferedImage[] left = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        BufferedImage[] right = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        Animation anim = new Animation(left, right);

        BufferedImage frame = anim.animate(1, false);
        assertNotNull(frame,
            "REQ-ANM-003: animate(toRight=false) phải trả BufferedImage");
    }

    @Test
    @DisplayName("REQ-ANM-004: Animation.getLeftFrames() và getRightFrames()")
    void reqAnm004_getFrames() {
        BufferedImage[] left = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        BufferedImage[] right = new BufferedImage[]{new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB)};
        Animation anim = new Animation(left, right);

        assertNotNull(anim.getLeftFrames(),
            "REQ-ANM-004: getLeftFrames() không được null");
        assertNotNull(anim.getRightFrames(),
            "REQ-ANM-004: getRightFrames() không được null");
    }

    // ============================================================
    // 5. Requirements-based Testing: MapSelection
    // ============================================================

    @Test
    @DisplayName("REQ-MS-001: MapSelection constructor tạo được instance")
    void reqMs001_mapSelectionInit() {
        MapSelection selection = new MapSelection();
        assertNotNull(selection,
            "REQ-MS-001: MapSelection phải tạo được instance");
    }

    @Test
    @DisplayName("REQ-MS-002: MapSelection.draw() với null Graphics không crash")
    void reqMs002_drawWithNullGraphics() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> selection.draw(null),
            "REQ-MS-002: draw(null) không crash");
    }

    @Test
    @DisplayName("REQ-MS-003: MapSelection.selectMap(Point) với null trả null")
    void reqMs003_selectMapNullPoint() {
        MapSelection selection = new MapSelection();
        String result = selection.selectMap((Point) null);
        assertNull(result,
            "REQ-MS-003: selectMap(null) trả null");
    }

    @Test
    @DisplayName("REQ-MS-004: MapSelection.changeSelectedMap với up=true")
    void reqMs004_changeSelectedMapUp() {
        MapSelection selection = new MapSelection();
        int result = selection.changeSelectedMap(0, true);
        assertTrue(result >= 0,
            "REQ-MS-004: changeSelectedMap(up=true) phải trả index >= 0");
    }

    @Test
    @DisplayName("REQ-MS-005: MapSelection.changeSelectedMap với up=false")
    void reqMs005_changeSelectedMapDown() {
        MapSelection selection = new MapSelection();
        int result = selection.changeSelectedMap(0, false);
        assertTrue(result >= 0,
            "REQ-MS-005: changeSelectedMap(up=false) phải trả index >= 0");
    }

    @Test
    @DisplayName("REQ-MS-006: MapSelection.selectMap(int) với index hợp lệ")
    void reqMs006_selectMapWithValidIndex() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> selection.selectMap(0),
            "REQ-MS-006: selectMap(0) không crash");
    }

    // ============================================================
    // 6. Requirements-based Testing: StartScreenSelection
    // ============================================================

    @Test
    @DisplayName("REQ-SS-001: StartScreenSelection.START_GAME tồn tại")
    void reqSs001_startGameExists() {
        assertNotNull(StartScreenSelection.START_GAME,
            "REQ-SS-001: START_GAME enum phải tồn tại");
    }

    @Test
    @DisplayName("REQ-SS-002: StartScreenSelection.VIEW_HELP tồn tại")
    void reqSs002_viewHelpExists() {
        assertNotNull(StartScreenSelection.VIEW_HELP,
            "REQ-SS-002: VIEW_HELP enum phải tồn tại");
    }

    @Test
    @DisplayName("REQ-SS-003: StartScreenSelection.VIEW_ABOUT tồn tại")
    void reqSs003_viewAboutExists() {
        assertNotNull(StartScreenSelection.VIEW_ABOUT,
            "REQ-SS-003: VIEW_ABOUT enum phải tồn tại");
    }

    @Test
    @DisplayName("REQ-SS-004: getLineNumber() trả về đúng giá trị")
    void reqSs004_getLineNumber() {
        assertEquals(0, StartScreenSelection.START_GAME.getLineNumber(),
            "REQ-SS-004: START_GAME.getLineNumber() phải bằng 0");
        assertEquals(1, StartScreenSelection.VIEW_HELP.getLineNumber(),
            "REQ-SS-004: VIEW_HELP.getLineNumber() phải bằng 1");
        assertEquals(2, StartScreenSelection.VIEW_ABOUT.getLineNumber(),
            "REQ-SS-004: VIEW_ABOUT.getLineNumber() phải bằng 2");
    }

    @Test
    @DisplayName("REQ-SS-005: select(true) di chuyển lên")
    void reqSs005_selectUp() {
        StartScreenSelection result = StartScreenSelection.START_GAME.select(true);
        assertNotNull(result,
            "REQ-SS-005: select(true) từ START_GAME phải trả giá trị");
    }

    @Test
    @DisplayName("REQ-SS-006: select(false) di chuyển xuống")
    void reqSs006_selectDown() {
        StartScreenSelection result = StartScreenSelection.START_GAME.select(false);
        assertNotNull(result,
            "REQ-SS-006: select(false) từ START_GAME phải trả giá trị");
    }

    @Test
    @DisplayName("REQ-SS-007: getSelection(int) trả giá trị hợp lệ")
    void reqSs007_getSelection() {
        assertDoesNotThrow(() -> StartScreenSelection.getSelection(0),
            "REQ-SS-007: getSelection(0) không crash");
    }

    // ============================================================
    // 7. Requirements-based Testing: SoundManager
    // ============================================================

    @Test
    @DisplayName("REQ-SM-001: SoundManager constructor tạo được instance")
    void reqSm001_soundManagerInit() {
        SoundManager sm = new SoundManager();
        assertNotNull(sm,
            "REQ-SM-001: SoundManager phải tạo được instance");
    }

    @Test
    @DisplayName("REQ-SM-002: SoundManager.playJump() không crash")
    void reqSm002_playJump() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playJump(),
            "REQ-SM-002: playJump() không crash");
    }

    @Test
    @DisplayName("REQ-SM-003: SoundManager.playCoin() không crash")
    void reqSm003_playCoin() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playCoin(),
            "REQ-SM-003: playCoin() không crash");
    }

    @Test
    @DisplayName("REQ-SM-004: SoundManager.playStomp() không crash")
    void reqSm004_playStomp() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playStomp(),
            "REQ-SM-004: playStomp() không crash");
    }

    @Test
    @DisplayName("REQ-SM-005: SoundManager.playMarioDies() không crash")
    void reqSm005_playMarioDies() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playMarioDies(),
            "REQ-SM-005: playMarioDies() không crash");
    }

    @Test
    @DisplayName("REQ-SM-006: SoundManager.playGameOver() không crash")
    void reqSm006_playGameOver() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playGameOver(),
            "REQ-SM-006: playGameOver() không crash");
    }

    @Test
    @DisplayName("REQ-SM-007: SoundManager.pauseBackground() không crash")
    void reqSm007_pauseBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.pauseBackground(),
            "REQ-SM-007: pauseBackground() không crash");
    }

    @Test
    @DisplayName("REQ-SM-008: SoundManager.resumeBackground() không crash")
    void reqSm008_resumeBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.resumeBackground(),
            "REQ-SM-008: resumeBackground() không crash");
    }

    @Test
    @DisplayName("REQ-SM-009: SoundManager.cleanupFinishedClips() không crash")
    void reqSm009_cleanupFinishedClips() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.cleanupFinishedClips(),
            "REQ-SM-009: cleanupFinishedClips() không crash");
    }

    @Test
    @DisplayName("REQ-SM-010: SoundManager.playFireFlower() không crash")
    void reqSm010_playFireFlower() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playFireFlower(),
            "REQ-SM-010: playFireFlower() không crash");
    }

    @Test
    @DisplayName("REQ-SM-011: SoundManager.playOneUp() không crash")
    void reqSm011_playOneUp() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playOneUp(),
            "REQ-SM-011: playOneUp() không crash");
    }

    @Test
    @DisplayName("REQ-SM-012: SoundManager.playSuperMushroom() không crash")
    void reqSm012_playSuperMushroom() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playSuperMushroom(),
            "REQ-SM-012: playSuperMushroom() không crash");
    }

    @Test
    @DisplayName("REQ-SM-013: SoundManager.playFireball() không crash")
    void reqSm013_playFireball() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playFireball(),
            "REQ-SM-013: playFireball() không crash");
    }

    @Test
    @DisplayName("REQ-SM-014: SoundManager.restartBackground() không crash")
    void reqSm014_restartBackground() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.restartBackground(),
            "REQ-SM-014: restartBackground() không crash");
    }

    @Test
    @DisplayName("REQ-SM-015: SoundManager.setVolume(float) không crash")
    void reqSm015_setVolume() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.setVolume(0.0f),
            "REQ-SM-015: setVolume(0.0f) không crash");
    }

    @Test
    @DisplayName("REQ-SM-016: SoundManager.setVolume với giá trị biên không crash")
    void reqSm016_setVolumeBoundary() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.setVolume(GameConstants.MAX_VOLUME),
            "REQ-SM-016: setVolume(MAX_VOLUME) không crash");
        assertDoesNotThrow(() -> sm.setVolume(GameConstants.MIN_VOLUME),
            "REQ-SM-016: setVolume(MIN_VOLUME) không crash");
    }

    // ============================================================
    // 8. Requirements-based Testing: MapRenderer
    // ============================================================

    @Test
    @DisplayName("REQ-MR-001: MapRenderer constructor tạo được instance")
    void reqMr001_mapRendererInit() {
        MapRenderer renderer = new MapRenderer();
        assertNotNull(renderer,
            "REQ-MR-001: MapRenderer phải tạo được instance");
    }

    @Test
    @DisplayName("REQ-MR-002: MapRenderer.render(null, null, null) không crash")
    void reqMr002_renderWithNull() {
        MapRenderer renderer = new MapRenderer();
        assertDoesNotThrow(() -> renderer.render(null, null, null),
            "REQ-MR-002: render(null, null, null) không crash");
    }

    // ============================================================
    // 9. Requirements-based Testing: HUDRenderer
    // ============================================================

    @Test
    @DisplayName("REQ-HUD-001: HUDRenderer constructor tạo được instance")
    void reqHud001_hudRendererInit() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertNotNull(renderer,
            "REQ-HUD-001: HUDRenderer phải tạo được instance");
    }

    @Test
    @DisplayName("REQ-HUD-002: HUDRenderer.render(null, ...) không crash")
    void reqHud002_renderWithNullGraphics() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.render(null, 0, 3, 0, 300),
            "REQ-HUD-002: render(null, ...) không crash");
    }

    @Test
    @DisplayName("REQ-HUD-003: HUDRenderer.drawVictoryScreen(null, ...) không crash")
    void reqHud003_drawVictoryScreenNull() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.drawVictoryScreen(null, 0, 0),
            "REQ-HUD-003: drawVictoryScreen(null, ...) không crash");
    }

    @Test
    @DisplayName("REQ-HUD-004: HUDRenderer.drawPauseScreen(null) không crash")
    void reqHud004_drawPauseScreenNull() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.drawPauseScreen(null),
            "REQ-HUD-004: drawPauseScreen(null) không crash");
    }

    @Test
    @DisplayName("REQ-HUD-005: HUDRenderer.drawGameOverScreen(null, ...) không crash")
    void reqHud005_drawGameOverScreenNull() {
        BufferedImage heart = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        BufferedImage coin = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.PLAIN, 16);

        HUDRenderer renderer = new HUDRenderer(font, heart, coin, 1268, 708);
        assertDoesNotThrow(() -> renderer.drawGameOverScreen(null, 0, 0),
            "REQ-HUD-005: drawGameOverScreen(null, ...) không crash");
    }

    // ============================================================
    // 10. Scenario Testing
    // ============================================================

    @Test
    @DisplayName("SCN-001: MapSelection chọn map với index hợp lệ")
    void scn001_selectMapWithValidIndex() {
        MapSelection selection = new MapSelection();
        String result = selection.selectMap(0);
        assertNotNull(result,
            "SCN-001: selectMap(0) với index hợp lệ phải trả tên map");
    }

    @Test
    @DisplayName("SCN-002: StartScreenSelection navigation wrap-around")
    void scn002_startScreenSelectionWrapAround() {
        StartScreenSelection result = StartScreenSelection.VIEW_ABOUT.select(false);
        assertEquals(StartScreenSelection.START_GAME, result,
            "SCN-002: Từ VIEW_ABOUT select(false) phải wrap về START_GAME");
    }

    @Test
    @DisplayName("SCN-003: SoundManager phát nhiều âm thanh liên tiếp")
    void scn003_multipleSounds() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            sm.playJump();
            sm.playCoin();
            sm.playStomp();
        }, "SCN-003: Phát nhiều âm thanh liên tiếp không crash");
    }

    @Test
    @DisplayName("SCN-004: Animation hoán đổi hướng liên tục")
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
        }, "SCN-004: Hoán đổi hướng liên tục không crash");
    }

    @Test
    @DisplayName("SCN-005: MapSelection thay đổi selection nhiều lần")
    void scn005_mapSelectionMultipleChanges() {
        MapSelection selection = new MapSelection();
        assertDoesNotThrow(() -> {
            selection.changeSelectedMap(0, true);
            selection.changeSelectedMap(0, false);
            selection.changeSelectedMap(0, true);
            selection.changeSelectedMap(0, false);
        }, "SCN-005: Thay đổi selection nhiều lần không crash");
    }

    @Test
    @DisplayName("SCN-006: StartScreenSelection điều hướng đầy đủ")
    void scn006_startScreenSelectionFullNavigation() {
        StartScreenSelection current = StartScreenSelection.START_GAME;
        for (int i = 0; i < 5; i++) {
            current = current.select(false);
        }
        assertNotNull(current,
            "SCN-006: Navigation nhiều lần vẫn không null");
    }

    @Test
    @DisplayName("SCN-007: SoundManager setVolume nhiều lần")
    void scn007_multipleVolumeChanges() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            sm.setVolume(-40.0f);
            sm.setVolume(-20.0f);
            sm.setVolume(0.0f);
            sm.setVolume(6.0f);
        }, "SCN-007: setVolume nhiều lần không crash");
    }

    // ============================================================
    // 11. Guideline-based Testing
    // ============================================================

    @Test
    @DisplayName("GUIDE-001: SoundManager phát âm thanh khi chưa pause")
    void guide001_playBeforePauseDoesNotCrash() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> sm.playJump(),
            "GUIDE-001: playJump() khi chưa pause không crash");
    }

    @Test
    @DisplayName("GUIDE-002: MapRenderer với null map không crash")
    void guide002_mapRendererNullMap() {
        MapRenderer renderer = new MapRenderer();
        assertDoesNotThrow(() -> renderer.render(null, null, null),
            "GUIDE-002: render(null) không crash");
    }

    @Test
    @DisplayName("GUIDE-003: ImageLoader.loadImage với path không tồn tại không crash")
    void guide003_loadImageInvalidPath() {
        ImageLoader loader = new ImageLoader();
        assertDoesNotThrow(() -> loader.loadImage("/nonexistent.png"),
            "GUIDE-003: loadImage(path không tồn tại) không crash");
    }

    @Test
    @DisplayName("GUIDE-004: Animation với mảng rỗng không crash")
    void guide004_animationEmptyArrays() {
        assertDoesNotThrow(() -> {
            Animation anim = new Animation(new BufferedImage[0], new BufferedImage[0]);
            anim.animate(1, true);
        }, "GUIDE-004: Animation với mảng rỗng không crash");
    }

    @Test
    @DisplayName("GUIDE-005: HUDRenderer với Graphics null không crash")
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
        }, "GUIDE-005: Tất cả method với null Graphics không crash");
    }

    @Test
    @DisplayName("GUIDE-006: SoundManager phát sound khi background null")
    void guide006_playSoundWhenBackgroundNull() {
        SoundManager sm = new SoundManager();
        assertDoesNotThrow(() -> {
            sm.pauseBackground();
            sm.playJump();
            sm.playCoin();
        }, "GUIDE-006: Phát sound sau pause không crash");
    }

    @Test
    @DisplayName("GUIDE-007: AssetManager gọi nhiều lần vẫn Singleton")
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
    @DisplayName("PERF-001: Animation.animate 1000 lần trong 500ms")
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
            "PERF-001: animate 1000 lần phải < 500ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-002: ImageLoader.getSubImage 1000 lần trong 500ms")
    void perf002_getSubImage1000Times() {
        ImageLoader loader = new ImageLoader();
        BufferedImage atlas = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            loader.getSubImage(atlas, 1, 1, 48, 48);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-002: getSubImage 1000 lần phải < 500ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-003: SoundManager.playJump 100 lần trong 500ms")
    void perf003_playJump100Times() {
        SoundManager sm = new SoundManager();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            sm.playJump();
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-003: playJump 100 lần phải < 500ms. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("PERF-004: MapSelection.changeSelectedMap 1000 lần trong 500ms")
    void perf004_changeSelectedMap1000Times() {
        MapSelection selection = new MapSelection();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            selection.changeSelectedMap(0, true);
            selection.changeSelectedMap(0, false);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 500,
            "PERF-004: changeSelectedMap 1000 lần phải < 500ms. Actual: " + elapsed + "ms");
    }
}
