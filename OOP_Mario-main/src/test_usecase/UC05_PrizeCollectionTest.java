package test_usecase.test_usecase;

import controller.CollisionSystem;
import event.EventBus;
import event.GameEvent;
import model.Map;
import model.hero.Mario;
import model.prize.Coin;
import model.prize.FireFlower;
import model.prize.OneUpMushroom;
import model.prize.SuperMushroom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Case cho UC05 — Mario Thu Thập Prize (Coin / Mushroom / FireFlower)
 * ─────────────────────────────────────────────────────────────────────────
 * Mục tiêu : Kiểm thử Mario thu thập Prize (Coin, SuperMushroom, FireFlower,
 *             OneUpMushroom) để nhận hiệu ứng (điểm, form upgrade, thêm mạng).
 *
 * Các test case :
 *   TC-UC05-001 : Coin khởi tạo với point đúng
 *   TC-UC05-002 : Coin.reveal() đặt isRevealed=true
 *   TC-UC05-003 : Coin.isRevealed ban đầu false
 *   TC-UC05-004 : Prize.setPoint() và getPoint() hoạt động
 *   TC-UC05-005 : SuperMushroom là instanceof Prize
 *   TC-UC05-006 : FireFlower là instanceof Prize
 *   TC-UC05-007 : OneUpMushroom là instanceof Prize
 *   TC-UC05-008 : Map.addRevealedPrize() thêm prize thành công
 *   TC-UC05-009 : Map.getRevealedPrizes() trả danh sách prize
 *   TC-UC05-010 : EventBus post COIN_COLLECTED được nhận
 *   TC-UC05-011 : SuperMushroom khởi tạo thành công
 *   TC-UC05-012 : FireFlower khởi tạo thành công
 *   TC-UC05-013 : OneUpMushroom khởi tạo thành công
 *
 * Tham chiếu : UC05_Sequence.puml
 */
public class UC05_PrizeCollectionTest {

    private CollisionSystem collisionSystem;
    private Map map;
    private Mario mario;
    private boolean coinEventFired;
    private boolean mushroomEventFired;
    private boolean fireFlowerEventFired;
    private boolean oneUpEventFired;

    @BeforeEach
    void setUp() {
        EventBus.getInstance().reset();
        collisionSystem = new CollisionSystem();
        map = new Map(300, null);
        assertDoesNotThrow(() -> mario = new Mario(100, 300));
        map.setMario(mario);

        coinEventFired = false;
        mushroomEventFired = false;
        fireFlowerEventFired = false;
        oneUpEventFired = false;

        EventBus.getInstance().subscribe(GameEvent.COIN_COLLECTED, d -> coinEventFired = true);
        EventBus.getInstance().subscribe(GameEvent.SUPER_MUSHROOM, d -> mushroomEventFired = true);
        EventBus.getInstance().subscribe(GameEvent.FIRE_FLOWER, d -> fireFlowerEventFired = true);
        EventBus.getInstance().subscribe(GameEvent.ONE_UP, d -> oneUpEventFired = true);
    }

    // TC-UC05-001
    @Test
    @DisplayName("TC-UC05-001: Coin khởi tạo với point đúng")
    void tcUc05001_coinInitWithPoint() {
        Coin coin = new Coin(100, 200, null, 100);
        assertEquals(100, coin.getPoint(),
            "TC-UC05-001: Coin point phải bằng 100");
    }

    // TC-UC05-002
    @Test
    @DisplayName("TC-UC05-002: Coin.reveal() đặt isRevealed=true")
    void tcUc05002_coinReveal() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.reveal();
        assertTrue(coin.isRevealed(),
            "TC-UC05-002: Coin sau reveal() phải isRevealed=true");
    }

    // TC-UC05-003
    @Test
    @DisplayName("TC-UC05-003: Coin.isRevealed ban đầu false")
    void tcUc05003_coinNotRevealedInitially() {
        Coin coin = new Coin(100, 200, null, 100);
        assertFalse(coin.isRevealed(),
            "TC-UC05-003: Coin ban đầu isRevealed phải false");
    }

    // TC-UC05-004
    @Test
    @DisplayName("TC-UC05-004: Prize.setPoint() và getPoint() hoạt động")
    void tcUc05004_prizePointAccessors() {
        Coin coin = new Coin(100, 200, null, 100);
        coin.setPoint(200);
        assertEquals(200, coin.getPoint(),
            "TC-UC05-004: setPoint(200) phải cập nhật getPoint()");
    }

    // TC-UC05-005
    @Test
    @DisplayName("TC-UC05-005: SuperMushroom là instanceof Prize")
    void tcUc05005_superMushroomIsPrize() {
        assertDoesNotThrow(() -> {
            SuperMushroom mushroom = new SuperMushroom(100, 200, null);
            assertTrue(mushroom instanceof model.prize.Prize,
                "TC-UC05-005: SuperMushroom phải là instanceof Prize");
        });
    }

    // TC-UC05-006
    @Test
    @DisplayName("TC-UC05-006: FireFlower là instanceof Prize")
    void tcUc05006_fireFlowerIsPrize() {
        assertDoesNotThrow(() -> {
            FireFlower flower = new FireFlower(100, 200, null);
            assertTrue(flower instanceof model.prize.Prize,
                "TC-UC05-006: FireFlower phải là instanceof Prize");
        });
    }

    // TC-UC05-007
    @Test
    @DisplayName("TC-UC05-007: OneUpMushroom là instanceof Prize")
    void tcUc05007_oneUpMushroomIsPrize() {
        assertDoesNotThrow(() -> {
            OneUpMushroom mushroom = new OneUpMushroom(100, 200, null);
            assertTrue(mushroom instanceof model.prize.Prize,
                "TC-UC05-007: OneUpMushroom phải là instanceof Prize");
        });
    }

    // TC-UC05-008
    @Test
    @DisplayName("TC-UC05-008: Map.addRevealedPrize() thêm prize thành công")
    void tcUc05008_mapAddRevealedPrize() {
        assertDoesNotThrow(() -> {
            Coin coin = new Coin(100, 200, null, 100);
            map.addRevealedPrize(coin);
            assertEquals(1, map.getRevealedPrizes().size(),
                "TC-UC05-008: revealedPrizes phải có 1 prize sau addRevealedPrize");
        });
    }

    // TC-UC05-009
    @Test
    @DisplayName("TC-UC05-009: Map.getRevealedPrizes() trả danh sách prize")
    void tcUc05009_getRevealedPrizes() {
        assertNotNull(map.getRevealedPrizes(),
            "TC-UC05-009: getRevealedPrizes() không được null");
    }

    // TC-UC05-010
    @Test
    @DisplayName("TC-UC05-010: EventBus post COIN_COLLECTED được nhận")
    void tcUc05010_coinEventReceived() {
        boolean[] fired = { false };
        EventBus.getInstance().subscribe(GameEvent.COIN_COLLECTED, d -> fired[0] = true);
        EventBus.getInstance().post(GameEvent.COIN_COLLECTED);
        assertTrue(fired[0],
            "TC-UC05-010: COIN_COLLECTED event phải được nhận");
    }

    // TC-UC05-011
    @Test
    @DisplayName("TC-UC05-011: SuperMushroom khởi tạo thành công")
    void tcUc05011_superMushroomInit() {
        assertDoesNotThrow(() -> new SuperMushroom(100, 200, null),
            "TC-UC05-011: SuperMushroom khởi tạo không crash");
    }

    // TC-UC05-012
    @Test
    @DisplayName("TC-UC05-012: FireFlower khởi tạo thành công")
    void tcUc05012_fireFlowerInit() {
        assertDoesNotThrow(() -> new FireFlower(100, 200, null),
            "TC-UC05-012: FireFlower khởi tạo không crash");
    }

    // TC-UC05-013
    @Test
    @DisplayName("TC-UC05-013: OneUpMushroom khởi tạo thành công")
    void tcUc05013_oneUpMushroomInit() {
        assertDoesNotThrow(() -> new OneUpMushroom(100, 200, null),
            "TC-UC05-013: OneUpMushroom khởi tạo không crash");
    }
}
