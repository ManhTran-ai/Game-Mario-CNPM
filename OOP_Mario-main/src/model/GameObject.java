package model;

import manager.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-07
 * Purpose: Abstract base class for all physical game entities (Mario, Enemy, Brick, Prize, Fireball, EndFlag). Encapsulates position, velocity, dimension, gravity physics, and directional bounding-box helpers.
 * Owner: Member 2
 * Ref UML: CD, OD
 * Derivation: Base of the entire object hierarchy; physics methods derived from AD01 update model.
 */
public class GameObject {

    private double x, y;
    private double velX, velY;
    private Dimension dimension;
    private BufferedImage style;
    private double gravityAcc;
    private boolean falling, jumping;

    /**
     * Method ID: MTH-001
     * Initializes position, style, and sets default physics (gravity, no velocity).
     * @param x     Initial x-coordinate.
     * @param y     Initial y-coordinate.
     * @param style BufferedImage sprite for rendering.
     */
    public GameObject(double x, double y, BufferedImage style) {
        setLocation(x, y);
        setStyle(style);
        if (style != null) {
            setDimension(style.getWidth(), style.getHeight());
        }
        setVelX(0);
        setVelY(0);
        setGravityAcc(GameConstants.GRAVITY);
        jumping = false;
        falling = true;
    }

    /**
     * Method ID: MTH-002
     * Renders the object's sprite at its current position.
     * @param g The Graphics context.
     */
    public void draw(Graphics g) {
        BufferedImage style = getStyle();
        if (style != null) {
            g.drawImage(style, (int) x, (int) y, null);
        }
    }

    /**
     * Method ID: MTH-003
     * Applies physics: jumping decelerates upward (velY decreases), falling accelerates downward (velY increases).
     * Horizontal position is updated by velX. State transitions occur at jump apex.
     */
    public void updateLocation() {
        if (jumping && velY <= 0) {
            jumping = false;
            falling = true;
        } else if (jumping) {
            velY = velY - gravityAcc;
            y = y - velY;
        }
        if (falling) {
            y = y + velY;
            velY = velY + gravityAcc;
        }

        x = x + velX;
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setDimension(int width, int height) {
        this.dimension = new Dimension(width, height);
    }

    public BufferedImage getStyle() {
        return style;
    }

    public void setStyle(BufferedImage style) {
        this.style = style;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    /** Full bounding rectangle. */
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, dimension.width, dimension.height);
    }

    /** Top third of the object, used for ceiling/bottom-head collision checks. */
    public Rectangle getTopBounds() {
        return new Rectangle((int) x + dimension.width / 6, (int) y, 2 * dimension.width / 3, dimension.height / 2);
    }

    /** Bottom third of the object, used for landing detection. */
    public Rectangle getBottomBounds() {
        return new Rectangle((int) x + dimension.width / 6, (int) y + dimension.height / 2, 2 * dimension.width / 3, dimension.height / 2);
    }

    /** Left quarter of the object, used for left-side collision checks. */
    public Rectangle getLeftBounds() {
        return new Rectangle((int) x, (int) y + dimension.height / 4, dimension.width / 4, dimension.height / 2);
    }

    /** Right quarter of the object, used for right-side collision checks. */
    public Rectangle getRightBounds() {
        return new Rectangle((int) x + 3 * dimension.width / 4, (int) y + dimension.height / 4, dimension.width / 4, dimension.height / 2);
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    /**
     * Method ID: MTH-004
     * Positions the object on top of a surface and resets vertical motion.
     * @param surfaceY The y-coordinate of the surface top.
     */
    public void landOnSurface(double surfaceY) {
        this.y = surfaceY - dimension.height;
        this.falling = false;
        this.velY = 0;
    }

    /**
     * Method ID: MTH-005
     * Stops upward motion and pushes object below a ceiling.
     * @param ceilingY The y-coordinate of the ceiling bottom.
     */
    public void hitCeiling(double ceilingY) {
        this.velY = 0;
        this.y = ceilingY + dimension.height;
    }

    public void stopHorizontal() {
        this.velX = 0;
    }

    /**
     * Method ID: MTH-006
     * Reverses vertical velocity so the object bounces downward.
     */
    public void bounce() {
        this.velY = Math.abs(this.velY);
        this.jumping = false;
        this.falling = true;
    }
}
