package manager;

import controller.CollisionSystem;
import controller.GameController;
import event.EventBus;
import event.GameEvent;
import model.Map;
import model.brick.OrdinaryBrick;
import model.hero.Fireball;
import model.hero.Mario;
import view.ImageLoader;
import view.MapRenderer;

import java.awt.*;

/**
 * Component ID: CLS-16
 * Purpose: Facade coordinating map lifecycle, per-tick updates, collision checks, mission completion, and score/lives/time tracking.
 * Owner: Member 4
 * Ref UML: CD, AD01, UC09, SD01, SD03
 * Derivation: Created by GameController; owns Map and CollisionSystem instances.
 */
public class MapManager {

    private Map map;
    private final CollisionSystem collisionSystem;
    private final MapRenderer mapRenderer;

    public MapManager() {
        this.collisionSystem = new CollisionSystem();
        this.mapRenderer = new MapRenderer();
    }

    public void updateLocations() {
        if (map == null)
            return;

        map.updateLocations();
    }

    /**
     * Method ID: MTH-001
     * Reloads the current map from disk and reattaches the existing Mario reference.
     */
    public void resetCurrentMap(GameController controller) {
        Mario mario = getMario();
        mario.resetLocation();
        controller.resetCamera();
        createMap(controller.getImageLoader(), map.getPath());
        map.setMario(mario);
    }

    /**
     * Method ID: MTH-002
     * Loads a map from a resource path using MapCreator.
     * @return true if load succeeded.
     */
    public boolean createMap(ImageLoader loader, String path) {
        MapCreator mapCreator = new MapCreator(loader);
        map = mapCreator.createMap("/maps/" + path, 400);

        return map != null;
    }

    public void acquirePoints(int point) {
        map.getMario().acquirePoints(point);
    }

    public Mario getMario() {
        return map.getMario();
    }

    /**
     * Method ID: MTH-003
     * Fires a fireball from Mario's current position if in FIRE form.
     */
    public void fire() {
        Fireball fireball = getMario().fire();
        if (fireball != null) {
            map.addFireball(fireball);
            EventBus.getInstance().post(GameEvent.FIREBALL_FIRED);
        }
    }

    public boolean isGameOver() {
        return getMario().getRemainingLives() == 0 || map.isTimeOver();
    }

    public int getScore() {
        return getMario().getPoints();
    }

    public int getRemainingLives() {
        return getMario().getRemainingLives();
    }

    public int getCoins() {
        return getMario().getCoins();
    }

    public void drawMap(Graphics2D g2, Graphics2D cameraG2) {
        mapRenderer.render(map, g2, cameraG2);
    }

    /**
     * Method ID: MTH-004
     * Triggers the end-flag fall animation and returns a score bonus based on completion height.
     * @return height * 2 bonus, or -1 if mission not yet triggered.
     */
    public int passMission() {
        if (getMario().getX() >= map.getEndPoint().getX() && !map.getEndPoint().isTouched()) {
            map.getEndPoint().setTouched(true);
            int height = (int) getMario().getY();
            return height * 2;
        } else {
            return -1;
        }
    }

    /**
     * Method ID: MTH-005
     * @return true when Mario has passed beyond the end flag.
     */
    public boolean endLevel() {
        return getMario().getX() >= map.getEndPoint().getX() + 320;
    }

    public void checkCollisions(GameController controller) {
        collisionSystem.checkAll(map, controller);
    }

    public void addRevealedBrick(OrdinaryBrick ordinaryBrick) {
        map.addRevealedBrick(ordinaryBrick);
    }

    public void updateTime() {
        if (map != null)
            map.updateTime(1);
    }

    public int getRemainingTime() {
        return (int) map.getRemainingTime();
    }
}
