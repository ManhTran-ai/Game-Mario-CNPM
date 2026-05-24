package model.prize;

import model.GameObject;
import model.hero.Mario;

import controller.GameController;

/**
 * Component ID: CLS-23
 * Purpose: Abstract base for all collectible power-up and coin items. Handles reveal state and removal criteria.
 * Owner: Member 4
 * Ref UML: OD, UC05, UC06
 * Derivation: Abstract onTouch method implemented per prize subtype; Coin and BoostItem are concrete variants.
 */
public abstract class Prize extends GameObject {

    private boolean revealed = false;
    private int point;

    public Prize(double x, double y, java.awt.image.BufferedImage style) {
        super(x, y, style);
    }

    /**
     * Method ID: MTH-001
     * Applies the prize's effect to Mario and publishes a sound event.
     * @param mario      The Mario instance collecting the prize.
     * @param controller GameController for event posting.
     */
    public abstract void onTouch(Mario mario, GameController controller);

    public boolean isDrawable() {
        return true;
    }

    /**
     * Method ID: MTH-002
     * @return True if the prize should be removed from the map.
     */
    public boolean shouldRemove() {
        return false;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    /**
     * Method ID: MTH-003
     * Marks the prize as revealed, starting its movement or draw.
     */
    public void reveal() {
        revealed = true;
    }
}
