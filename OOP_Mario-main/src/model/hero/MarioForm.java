package model.hero;

import manager.GameConstants;
import view.Animation;
import view.AssetManager;
import view.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-10
 * Purpose: Encapsulates Mario's current power-up state (SMALL, SUPER, FIRE) and provides sprite animation logic based on movement direction.
 * Owner: Member 3
 * Ref UML: OD, UC06
 * Derivation: State transitions triggered by BoostItem.onTouch; sprite frames fetched from ImageLoader per form type.
 */
public class MarioForm {

    public static final int SMALL = 0, SUPER = 1, FIRE = 2;

    private Animation animation;
    private boolean isSuper, isFire;
    private BufferedImage fireballStyle;

    /**
     * Method ID: MTH-001
     * @param animation Frame animation for the current form.
     * @param isSuper   True if Mario is Super Mario.
     * @param isFire    True if Mario is Fire Mario.
     */
    public MarioForm(Animation animation, boolean isSuper, boolean isFire) {
        this.animation = animation;
        this.isSuper = isSuper;
        this.isFire = isFire;

        ImageLoader imageLoader = AssetManager.getInstance().getImageLoader();
        BufferedImage sprite = imageLoader.loadImage("/sprite.png");
        fireballStyle = imageLoader.getSubImage(sprite, 3, 4, 24, 24);
    }

    /**
     * Method ID: MTH-002
     * Selects the appropriate sprite frame based on movement and direction.
     * @param toRight    True if Mario faces right.
     * @param movingInX  True if Mario is moving horizontally.
     * @param movingInY  True if Mario is jumping/falling.
     * @return The BufferedImage sprite for the current animation frame.
     */
    public BufferedImage getCurrentStyle(boolean toRight, boolean movingInX, boolean movingInY) {

        BufferedImage style;

        if (movingInY && toRight) {
            style = animation.getRightFrames()[0];
        } else if (movingInY) {
            style = animation.getLeftFrames()[0];
        } else if (movingInX) {
            style = animation.animate(GameConstants.ANIMATION_SPEED, toRight);
        } else {
            if (toRight) {
                style = animation.getRightFrames()[1];
            } else {
                style = animation.getLeftFrames()[1];
            }
        }

        return style;
    }

    /**
     * Method ID: MTH-003
     * Downgrades Mario to SMALL form when hit by an enemy while not super/fire.
     * @param imageLoader ImageLoader for loading SMALL sprite frames.
     * @return A new MarioForm in SMALL state.
     */
    public MarioForm onTouchEnemy(ImageLoader imageLoader) {
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(0);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(0);

        Animation newAnimation = new Animation(leftFrames, rightFrames);

        return new MarioForm(newAnimation, false, false);
    }

    /**
     * Method ID: MTH-004
     * Creates a fireball when Mario is in FIRE form and fires in the given direction.
     * @param toRight Direction Mario is facing.
     * @param x       x spawn position.
     * @param y       y spawn position.
     * @return A new Fireball or null if not in FIRE form.
     */
    public Fireball fire(boolean toRight, double x, double y) {
        if (isFire) {
            return new Fireball(x, y + GameConstants.BRICK_SIZE, fireballStyle, toRight);
        }
        return null;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }

    public boolean isFire() {
        return isFire;
    }
}
