package model.hero;

import manager.GameConstants;
import model.GameObject;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-11
 * Purpose: Projectile fired by Fire Mario. Travels horizontally until hitting a brick or enemy.
 * Owner: Member 3
 * Ref UML: OD, UC07
 * Derivation: Created via MarioForm.fire() when Mario is in FIRE form.
 */
public class Fireball extends GameObject {

    /**
     * Method ID: MTH-001
     * Initializes fireball at the given position, moving horizontally.
     * @param x      Initial x-coordinate.
     * @param y      Initial y-coordinate.
     * @param style  Sprite image.
     * @param toRight True for rightward, false for leftward.
     */
    public Fireball(double x, double y, BufferedImage style, boolean toRight) {
        super(x, y, style);
        setDimension(GameConstants.FIREBALL_SIZE, GameConstants.FIREBALL_SIZE);
        setFalling(false);
        setJumping(false);
        setVelX(GameConstants.FIREBALL_SPEED);

        if (!toRight)
            setVelX(-GameConstants.FIREBALL_SPEED);
    }
}
