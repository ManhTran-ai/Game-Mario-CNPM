package controller;

import event.EventBus;
import event.GameEvent;
import manager.GameConstants;
import model.GameObject;
import model.Map;
import model.brick.Brick;
import model.Enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.prize.BoostItem;
import model.prize.Prize;

import java.awt.*;
import java.util.ArrayList;

/**
 * Component ID: CLS-06
 * Purpose: Detects and resolves all physical collisions in the game: Mario vs bricks/enemies, enemies vs bricks, prizes vs bricks, fireballs vs enemies/bricks. Acts as a stateless service invoked each game tick.
 * Owner: Member 2
 * Ref UML: CD, AD01, UC03, UC04, UC05, UC06
 * Derivation: Collision resolution logic extracted from AD01 update phase into dedicated subsystem.
 */
public class CollisionSystem {

    /**
     * Method ID: MTH-001
     * Runs the full suite of collision checks in sequence.
     * @param map        The current map state.
     * @param controller GameController for map-reset and prize reveal callbacks.
     */
    public void checkAll(Map map, GameController controller) {
        if (map == null) return;

        checkBottomCollisions(map, controller);
        checkTopCollisions(map, controller);
        checkMarioHorizontalCollision(map, controller);
        checkEnemyCollisions(map);
        checkPrizeCollision(map);
        checkPrizeContact(map, controller);
        checkFireballContact(map);
    }

    /**
     * Method ID: MTH-002
     * Detects Mario landing on bricks/enemies (bottom collision) and enemy stomping.
     */
    private void checkBottomCollisions(Map map, GameController controller) {
        Mario mario = map.getMario();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        Rectangle marioBottomBounds = mario.getBottomBounds();

        if (!mario.isJumping())
            mario.setFalling(true);

        for (Brick brick : bricks) {
            Rectangle brickBounds = brick.getBounds();
            if (marioBottomBounds.intersects(brickBounds)) {
                mario.landOnSurface(brick.getY() + 1);
            }
        }

        for (Enemy enemy : enemies) {
            Rectangle enemyTopBounds = enemy.getTopBounds();
            if (marioBottomBounds.intersects(enemyTopBounds)) {
                mario.acquirePoints(GameConstants.STOMP_POINTS);
                toBeRemoved.add(enemy);
                EventBus.getInstance().post(GameEvent.STOMP_ENEMY);
            }
        }

        if (mario.getY() + mario.getDimension().height >= map.getBottomBorder()) {
            mario.landOnSurface(map.getBottomBorder());
        }

        removeObjects(map, toBeRemoved);
    }

    /**
     * Method ID: MTH-003
     * Detects Mario hitting bricks from below (ceiling collision), which triggers prize reveal.
     */
    private void checkTopCollisions(Map map, GameController controller) {
        Mario mario = map.getMario();
        ArrayList<Brick> bricks = map.getAllBricks();

        Rectangle marioTopBounds = mario.getTopBounds();
        for (Brick brick : bricks) {
            Rectangle brickBottomBounds = brick.getBottomBounds();
            if (marioTopBounds.intersects(brickBottomBounds) && mario.getVelY() > 0) {
                mario.hitCeiling(brick.getY());
                Prize prize = brick.reveal(controller);
                if (prize != null)
                    map.addRevealedPrize(prize);
            }
        }
    }

    /**
     * Method ID: MTH-004
     * Detects Mario moving into bricks or enemies horizontally. Also clamps Mario at the left camera boundary.
     */
    private void checkMarioHorizontalCollision(Map map, GameController controller) {
        Mario mario = map.getMario();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        boolean toRight = mario.getToRight();

        Rectangle marioBounds = toRight ? mario.getRightBounds() : mario.getLeftBounds();

        for (Brick brick : bricks) {
            Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
            if (marioBounds.intersects(brickBounds) && Math.abs(mario.getVelX()) > 0) {
                if (toRight)
                    mario.stopAtX(brick.getX() - mario.getDimension().width);
                else
                    mario.stopAtX(brick.getX() + brick.getDimension().width);
            }
        }

        for (Enemy enemy : enemies) {
            Rectangle enemyBounds = !toRight ? enemy.getRightBounds() : enemy.getLeftBounds();
            if (marioBounds.intersects(enemyBounds)) {
                if (mario.onTouchEnemy()) {
                    controller.resetCurrentMap();
                }
                toBeRemoved.add(enemy);
            }
        }
        removeObjects(map, toBeRemoved);

        if (mario.getX() <= controller.getCameraLocation().getX() && mario.getVelX() < 0) {
            mario.stopAtLeftBoundary(controller.getCameraLocation().getX());
        }
    }

    /**
     * Method ID: MTH-005
     * Reverses enemy direction on brick side-contact and sets falling state when airborne.
     */
    private void checkEnemyCollisions(Map map) {
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();

        for (Enemy enemy : enemies) {
            boolean standsOnBrick = false;

            for (Brick brick : bricks) {
                Rectangle enemyBounds = enemy.getLeftBounds();
                Rectangle brickBounds = brick.getRightBounds();

                Rectangle enemyBottomBounds = enemy.getBottomBounds();
                Rectangle brickTopBounds = brick.getTopBounds();

                if (enemy.getVelX() > 0) {
                    enemyBounds = enemy.getRightBounds();
                    brickBounds = brick.getLeftBounds();
                }

                if (enemyBounds.intersects(brickBounds)) {
                    enemy.reverseDirection();
                }

                if (enemyBottomBounds.intersects(brickTopBounds)) {
                    enemy.landOnSurface(brick.getY());
                    standsOnBrick = true;
                }
            }

            if (enemy.getY() + enemy.getDimension().height > map.getBottomBorder()) {
                enemy.landOnSurface(map.getBottomBorder());
            }

            if (!standsOnBrick && enemy.getY() < map.getBottomBorder()) {
                enemy.setFalling(true);
            }
        }
    }

    /**
     * Method ID: MTH-006
     * Moves BoostItem (mushrooms, fireflower) vertically and reverses on brick sides.
     */
    private void checkPrizeCollision(Map map) {
        ArrayList<Prize> prizes = map.getRevealedPrizes();
        ArrayList<Brick> bricks = map.getAllBricks();

        for (Prize prize : prizes) {
            if (prize instanceof BoostItem) {
                BoostItem boost = (BoostItem) prize;
                Rectangle prizeBottomBounds = boost.getBottomBounds();
                Rectangle prizeRightBounds = boost.getRightBounds();
                Rectangle prizeLeftBounds = boost.getLeftBounds();
                boost.setFalling(true);

                for (Brick brick : bricks) {
                    Rectangle brickBounds;

                    if (boost.isFalling()) {
                        brickBounds = brick.getTopBounds();

                        if (brickBounds.intersects(prizeBottomBounds)) {
                            boost.landOnSurface(brick.getY());
                            if (boost.getVelX() == 0)
                                boost.setVelX(2);
                        }
                    }

                    if (boost.getVelX() > 0) {
                        brickBounds = brick.getLeftBounds();

                        if (brickBounds.intersects(prizeRightBounds)) {
                            boost.setVelX(-boost.getVelX());
                        }
                    } else if (boost.getVelX() < 0) {
                        brickBounds = brick.getRightBounds();

                        if (brickBounds.intersects(prizeLeftBounds)) {
                            boost.setVelX(-boost.getVelX());
                        }
                    }
                }

                if (boost.getY() + boost.getDimension().height > map.getBottomBorder()) {
                    boost.landOnSurface(map.getBottomBorder());
                    if (boost.getVelX() == 0)
                        boost.setVelX(2);
                }
            }
        }
    }

    /**
     * Method ID: MTH-007
     * Detects Mario making contact with revealed prizes and triggers their effect.
     */
    private void checkPrizeContact(Map map, GameController controller) {
        ArrayList<Prize> prizes = map.getRevealedPrizes();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        Rectangle marioBounds = map.getMario().getBounds();
        for (Prize prize : prizes) {
            Rectangle prizeBounds = prize.getBounds();
            if (prizeBounds.intersects(marioBounds)) {
                prize.onTouch(map.getMario(), controller);
                toBeRemoved.add(prize);
            }
        }

        removeObjects(map, toBeRemoved);
    }

    /**
     * Method ID: MTH-008
     * Detects fireballs hitting enemies or bricks; removes both on impact.
     */
    private void checkFireballContact(Map map) {
        ArrayList<Fireball> fireballs = map.getFireballs();
        ArrayList<Enemy> enemies = map.getEnemies();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        for (Fireball fireball : fireballs) {
            Rectangle fireballBounds = fireball.getBounds();

            if (fireball.getX() < map.getMario().getX() - GameConstants.SCREEN_WIDTH) {
                toBeRemoved.add(fireball);
                continue;
            }

            for (Enemy enemy : enemies) {
                Rectangle enemyBounds = enemy.getBounds();
                if (fireballBounds.intersects(enemyBounds)) {
                    map.getMario().acquirePoints(GameConstants.STOMP_POINTS);
                    toBeRemoved.add(enemy);
                    toBeRemoved.add(fireball);
                }
            }

            for (Brick brick : bricks) {
                Rectangle brickBounds = brick.getBounds();
                if (fireballBounds.intersects(brickBounds)) {
                    toBeRemoved.add(fireball);
                }
            }
        }

        removeObjects(map, toBeRemoved);
    }

    /**
     * Method ID: MTH-009
     * Removes collected objects from their respective map lists.
     * @param list Objects to remove.
     */
    private void removeObjects(Map map, ArrayList<GameObject> list) {
        if (list == null) return;

        for (GameObject object : list) {
            if (object instanceof Fireball) {
                map.removeFireball((Fireball) object);
            } else if (object instanceof Enemy) {
                map.removeEnemy((Enemy) object);
            } else if (object instanceof Prize) {
                map.removePrize((Prize) object);
            }
        }
    }
}
