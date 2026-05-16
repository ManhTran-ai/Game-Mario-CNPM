package model.brick;

import manager.GameConstants;
import model.GameObject;
import model.prize.Prize;

import controller.GameController;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-18
 * Purpose: Abstract base for all brick types. Defines breakable/empty state and provides a hook for prize reveal.
 * Owner: Member 4
 * Ref UML: OD, CD, UC03, UC05
 * Derivation: Polymorphic Brick hierarchy for ordinary, surprise, pipe, and ground brick variants.
 */
public abstract class Brick extends GameObject {

    private boolean breakable;
    private boolean empty;

    public Brick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Method ID: MTH-001
     * Called when Mario hits the brick from below. Returns the Prize to reveal, or null.
     * @param controller GameController for accessing MapManager.
     * @return Prize to add to the map, or null.
     */
    public Prize reveal(GameController controller) {
        return null;
    }

    public Prize getPrize() {
        return null;
    }
}
