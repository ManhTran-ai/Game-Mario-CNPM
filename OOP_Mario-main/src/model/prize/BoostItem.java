package model.prize;

import manager.GameConstants;
import model.hero.Mario;

import controller.GameController;

import java.awt.*;

/**
 * Component ID: CLS-24
 * Purpose: Abstract base for Mushroom and FireFlower items. Moves horizontally after being revealed and bounces off brick sides.
 * Owner: Member 4
 * Ref UML: OD, UC06
 * Derivation: Inherits reveal() offset logic; only moves horizontally when landed on a surface.
 */
public abstract class BoostItem extends Prize {

    public BoostItem(double x, double y, java.awt.image.BufferedImage style) {
        super(x, y, style);
        setDimension(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
    }

    public abstract void onTouch(Mario mario, GameController controller);

    /**
     * Method ID: MTH-001
     * Updates position only when revealed; otherwise stays dormant.
     */
    @Override
    public void updateLocation() {
        if (isRevealed()) {
            super.updateLocation();
        }
    }

    /**
     * Method ID: MTH-002
     * Draws only when revealed.
     */
    @Override
    public void draw(Graphics g) {
        if (isRevealed()) {
            g.drawImage(getStyle(), (int) getX(), (int) getY(), null);
        }
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    /**
     * Method ID: MTH-003
     * Reveals the item and offsets it upward to simulate emerging from the brick.
     */
    @Override
    public void reveal() {
        setY(getY() - GameConstants.PRIZE_REVEAL_OFFSET);
        setRevealed(true);
    }
}
