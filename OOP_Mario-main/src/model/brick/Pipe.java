package model.brick;

import manager.GameConstants;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-22
 * Purpose: Static vertical pipe that acts as a solid obstacle. Non-breakable, non-interactive.
 * Owner: Member 4
 * Ref UML: OD, SD02
 * Derivation: Sized 2x2 bricks; serves as ground decoration and collision boundary.
 */
public class Pipe extends Brick {

    public Pipe(double x, double y, BufferedImage style) {
        super(x, y, style);
        setBreakable(false);
        setEmpty(true);
        setDimension(GameConstants.PIPE_SIZE, GameConstants.PIPE_SIZE);
    }
}
