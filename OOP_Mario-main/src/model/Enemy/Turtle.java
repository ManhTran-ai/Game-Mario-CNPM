package model.Enemy;

import manager.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-13
 * Purpose: Enemy that patrols left-right. Renders directional sprite based on velocity.
 * Owner: Member 3
 * Ref UML: OD, UC04
 * Derivation: Turtle is the only Enemy subtype; left/right sprites set via MapCreator.
 */
public class Turtle extends Enemy {

    private BufferedImage leftImage;
    private BufferedImage rightImage;

    public Turtle(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(GameConstants.ENEMY_MOVE_SPEED);
    }

    public void setRightImage(BufferedImage image) {
        this.rightImage = image;
    }

    public void setLeftImage(BufferedImage image) {
        this.leftImage = image;
    }

    /**
     * Method ID: MTH-001
     * Renders left or right sprite depending on current horizontal direction.
     */
    @Override
    public void draw(Graphics g) {
        BufferedImage sprite = (getVelX() > 0) ? rightImage : leftImage;
        if (sprite != null) {
            g.drawImage(sprite, (int) getX(), (int) getY(), null);
        } else {
            super.draw(g);
        }
    }
}
