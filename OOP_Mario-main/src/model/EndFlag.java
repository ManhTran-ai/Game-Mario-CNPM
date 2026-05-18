package model;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-14
 * Purpose: Represents the end-of-level flag that triggers level completion when Mario reaches it.
 * Owner: Member 3
 * Ref UML: OD, UC09
 * Derivation: Flag starts fixed, then falls (touched=true) and anchors at a fixed y after animation.
 */
public class EndFlag extends GameObject {

    private boolean touched = false;

    public EndFlag(double x, double y, BufferedImage style) {
        super(x, y, style);
    }

    /**
     * Method ID: MTH-001
     * Anchors flag position after it falls past a boundary.
     */
    @Override
    public void updateLocation() {
        if (touched) {
            if (getY() + getDimension().getHeight() >= 576) {
                setFalling(false);
                setVelY(0);
                setY(576 - getDimension().getHeight());
            }
            super.updateLocation();
        }
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
