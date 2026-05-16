package model.Enemy;

import model.GameObject;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-12
 * Purpose: Base class for all enemy entities. Enemies move horizontally, reverse on brick contact, and fall under gravity.
 * Owner: Member 3
 * Ref UML: OD, UC04
 * Derivation: Shared movement behavior extracted from Turtle.
 */
public class Enemy extends GameObject {

    public Enemy(double x, double y, BufferedImage style) {
        super(x, y, style);
        setFalling(false);
        setJumping(false);
    }

    /**
     * Method ID: MTH-001
     * Inverts horizontal velocity to reverse patrol direction.
     */
    public void reverseDirection() {
        setVelX(-getVelX());
    }
}
