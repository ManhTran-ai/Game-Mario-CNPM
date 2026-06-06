package model.hero;

import event.EventBus;
import event.GameEvent;
import manager.Camera;
import manager.GameConstants;
import view.Animation;
import model.GameObject;
import view.AssetManager;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-09
 * Purpose: The player character. Manages movement, jumping, form upgrades, scoring, lives, and collision responses.
 * Owner: Member 3
 * Ref UML: OD, CD, UC02, UC03, UC05, UC06, UC07, UC08
 * Derivation: Core entity with references to MarioForm for state, Animation for sprites, Camera for movement bounds.
 */
public class Mario extends GameObject {

    private int remainingLives;
    private int coins;
    private int points;
    private double invincibilityTimer;
    private MarioForm marioForm;
    private boolean toRight = true;

    /**
     * Method ID: MTH-001
     * Initializes Mario as SMALL form at the given spawn position.
     * @param x Initial x-coordinate.
     * @param y Initial y-coordinate.
     */
    public Mario(double x, double y) {
        super(x, y, null);
        setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);

        remainingLives = 10;
        points = 0;
        coins = 0;
        invincibilityTimer = 0;

        ImageLoader imageLoader = AssetManager.getInstance().getImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);

        Animation animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, false, false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    /**
     * Method ID: MTH-002
     * Updates Mario sprite based on current movement and direction.
     */
    @Override
    public void draw(Graphics g) {
        boolean movingInX = (getVelX() != 0);
        boolean movingInY = (getVelY() != 0);

        setStyle(marioForm.getCurrentStyle(toRight, movingInX, movingInY));

        super.draw(g);
    }

    /**
     * Method ID: MTH-003
     * Initiates a jump if Mario is grounded. Posts JUMP event for sound.
     */
    public void jump() {
        if (!isJumping() && !isFalling()) {
            setJumping(true);
            setVelY(GameConstants.MARIO_JUMP_VELOCITY);
            EventBus.getInstance().post(GameEvent.JUMP);
        }
    }

    /**
     * Method ID: MTH-004
     * Moves Mario horizontally; right movement is always allowed, left movement is blocked by camera left boundary.
     * @param toRight True for rightward movement.
     * @param camera  Current camera for left-boundary check.
     */
    public void move(boolean toRight, Camera camera) {
        if (toRight) {
            setVelX(GameConstants.MARIO_MOVE_SPEED);
        } else if (camera.getX() < getX()) {
            setVelX(-GameConstants.MARIO_MOVE_SPEED);
        }

        this.toRight = toRight;
    }

    /**
     * Method ID: MTH-005
     * Handles collision with an enemy. Posts MARIO_DIES if SMALL and no invincibility; otherwise downgrades form and posts CAMERA_SHAKE.
     * @return True if Mario should die (map reset), false if form downgrade occurred.
     */
    public boolean onTouchEnemy() {
        if (!marioForm.isSuper() && !marioForm.isFire()) {
            EventBus.getInstance().post(GameEvent.MARIO_DIES);
            return true;
        } else {
            EventBus.getInstance().post(GameEvent.CAMERA_SHAKE);
            ImageLoader imageLoader = AssetManager.getInstance().getImageLoader();
            marioForm = marioForm.onTouchEnemy(imageLoader);
            setDimension(GameConstants.SMALL_MARIO_WIDTH, GameConstants.SMALL_MARIO_HEIGHT);
            return false;
        }
    }

    /**
     * Method ID: MTH-006
     * Fires a fireball if Mario is in FIRE form.
     * @return Fireball instance or null.
     */
    public Fireball fire() {
        return marioForm.fire(toRight, getX(), getY());
    }

    public void acquireCoin() {
        coins++;
    }

    public void acquirePoints(int point) {
        points = points + point;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public int getPoints() {
        return points;
    }

    public int getCoins() {
        return coins;
    }

    public MarioForm getMarioForm() {
        return marioForm;
    }

    public void setMarioForm(MarioForm marioForm) {
        this.marioForm = marioForm;
    }

    public boolean isSuper() {
        return marioForm.isSuper();
    }

    public boolean getToRight() {
        return toRight;
    }

    /**
     * Method ID: MTH-007
     * Resets Mario position and velocity to spawn state without resetting score/lives.
     */
    public void resetLocation() {
        setVelX(0);
        setVelY(0);
        setX(50);
        setJumping(false);
        setFalling(true);
    }

    public void stopAtX(double newX) {
        setX(newX);
        setVelX(0);
    }

    /**
     * Method ID: MTH-008
     * Clamps Mario at the left edge of the camera viewport.
     * @param boundaryX The leftmost visible x-coordinate.
     */
    public void stopAtLeftBoundary(double boundaryX) {
        if (getVelX() < 0) {
            setX(boundaryX);
            setVelX(0);
        }
    }
}
