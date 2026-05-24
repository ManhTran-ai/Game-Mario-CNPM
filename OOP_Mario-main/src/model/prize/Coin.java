package model.prize;

import event.EventBus;
import event.GameEvent;
import manager.GameConstants;
import model.hero.Mario;

import controller.GameController;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-25
 * Purpose: Collectible coin that awards points and increments coin counter when Mario touches it.
 * Owner: Member 4
 * Ref UML: OD, UC05
 * Derivation: Coin rises briefly after reveal, then disappears; onTouch posts COIN_COLLECTED event.
 */
public class Coin extends Prize {

    private boolean acquired = false;
    private int revealBoundary;

    public Coin(double x, double y, BufferedImage style, int point) {
        super(x, y, style);
        setPoint(point);
        setDimension(GameConstants.COIN_WIDTH, GameConstants.COIN_HEIGHT);
        revealBoundary = (int) getY() - getDimension().height;
    }

    /**
     * Method ID: MTH-001
     * Awards points and coin count; posts COIN_COLLECTED event.
     */
    @Override
    public void onTouch(Mario mario, GameController controller) {
        if (!acquired) {
            acquired = true;
            mario.acquirePoints(getPoint());
            mario.acquireCoin();
            EventBus.getInstance().post(GameEvent.COIN_COLLECTED);
        }
    }

    @Override
    public void reveal() {
        super.reveal();
    }

    /**
     * Method ID: MTH-002
     * Moves coin upward during reveal animation.
     */
    @Override
    public void updateLocation() {
        if (isRevealed()) {
            setY(getY() - GameConstants.COIN_REVEAL_PIXELS);
        }
    }

    /**
     * Method ID: MTH-003
     * Removes coin once it has risen past its reveal boundary.
     */
    @Override
    public boolean shouldRemove() {
        return isRevealed() && getRevealBoundary() > getY();
    }

    /**
     * Method ID: MTH-004
     * Draws coin only when revealed.
     */
    @Override
    public void draw(Graphics g) {
        if (isRevealed()) {
            g.drawImage(getStyle(), (int) getX(), (int) getY(), null);
        }
    }

    public int getRevealBoundary() {
        return revealBoundary;
    }
}
