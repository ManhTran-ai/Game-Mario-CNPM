package model;

import manager.GameConstants;
import model.brick.Brick;
import model.brick.OrdinaryBrick;
import model.Enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.prize.Prize;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Component ID: CLS-15
 * Purpose: Aggregates all game entities for a single level: Mario, bricks, enemies, prizes, fireballs, and end flag. Provides accessors and manages per-tick updates.
 * Owner: Member 4
 * Ref UML: OD, SD01, SD02, SD03
 * Derivation: Built by MapCreator from pixel-color map images; owned by MapManager.
 */
public class Map {

    private double remainingTime;
    private Mario mario;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Brick> groundBricks = new ArrayList<>();
    private ArrayList<Prize> revealedPrizes = new ArrayList<>();
    private ArrayList<Brick> revealedBricks = new ArrayList<>();
    private ArrayList<Fireball> fireballs = new ArrayList<>();
    private EndFlag endPoint;
    private BufferedImage backgroundImage;
    private double bottomBorder = GameConstants.SCREEN_HEIGHT - (int) GameConstants.BOTTOM_BORDER_OFFSET;
    private String path;

    public Map(double remainingTime, BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.remainingTime = remainingTime;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    public ArrayList<Prize> getRevealedPrizes() {
        return revealedPrizes;
    }

    /**
     * Method ID: MTH-001
     * Returns the union of regular bricks and ground bricks for collision checks.
     */
    public ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();
        allBricks.addAll(bricks);
        allBricks.addAll(groundBricks);
        return allBricks;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public ArrayList<Brick> getGroundBricks() {
        return groundBricks;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    public void addGroundBrick(Brick brick) {
        this.groundBricks.add(brick);
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Method ID: MTH-002
     * Updates all movable entities: Mario, enemies, prizes, fireballs, revealed brick animations, and the end flag.
     */
    public void updateLocations() {
        mario.updateLocation();
        for (Enemy enemy : enemies) {
            enemy.updateLocation();
        }

        for (Iterator<Prize> prizeIterator = revealedPrizes.iterator(); prizeIterator.hasNext(); ) {
            Prize prize = prizeIterator.next();
            prize.updateLocation();
            if (prize.shouldRemove()) {
                prizeIterator.remove();
            }
        }

        for (Fireball fireball : fireballs) {
            fireball.updateLocation();
        }

        animateRevealedBricks();

        endPoint.updateLocation();
    }

    /**
     * Method ID: MTH-003
     * Steps through break animation for OrdinaryBricks; removes when frames exhausted.
     */
    public void animateRevealedBricks() {
        for (Iterator<Brick> brickIterator = revealedBricks.iterator(); brickIterator.hasNext(); ) {
            OrdinaryBrick brick = (OrdinaryBrick) brickIterator.next();
            brick.animate();
            if (brick.getFrames() < 0) {
                bricks.remove(brick);
                brickIterator.remove();
            }
        }
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void addRevealedPrize(Prize prize) {
        revealedPrizes.add(prize);
    }

    public void addFireball(Fireball fireball) {
        fireballs.add(fireball);
    }

    public void setEndPoint(EndFlag endPoint) {
        this.endPoint = endPoint;
    }

    public EndFlag getEndPoint() {
        return endPoint;
    }

    public void addRevealedBrick(OrdinaryBrick ordinaryBrick) {
        revealedBricks.add(ordinaryBrick);
    }

    public void removeFireball(Fireball object) {
        fireballs.remove(object);
    }

    public void removeEnemy(Enemy object) {
        enemies.remove(object);
    }

    public void removePrize(Prize object) {
        revealedPrizes.remove(object);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Method ID: MTH-004
     * Decrements remaining time by the given amount (in seconds per tick).
     * @param passed Seconds to subtract.
     */
    public void updateTime(double passed) {
        remainingTime = remainingTime - passed;
    }

    public boolean isTimeOver() {
        return remainingTime <= 0;
    }

    public double getRemainingTime() {
        return remainingTime;
    }
}
