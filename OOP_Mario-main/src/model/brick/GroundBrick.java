package model.brick;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-21
 * Purpose: Non-interactive floor/wall brick with no collision behavior.
 * Owner: Member 4
 * Ref UML: OD, SD02
 * Derivation: Purely decorative brick type used for ground tiles.
 */
public class GroundBrick extends Brick {

    public GroundBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setBreakable(false);
        setEmpty(true);
    }
}
