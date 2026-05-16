package model.brick;

import manager.GameConstants;
import manager.MapManager;
import model.prize.Prize;
import view.Animation;
import view.AssetManager;
import view.ImageLoader;

import controller.GameController;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-19
 * Purpose: Breakable brick that plays an animation and is removed when Mario is Super/Fire and hits it from below. Invisible coin-only variant for SMALL Mario.
 * Owner: Member 4
 * Ref UML: OD, UC05
 * Derivation: Breakable behavior triggered only when Mario is not SMALL; otherwise no effect.
 */
public class OrdinaryBrick extends Brick {

    private Animation animation;
    private boolean breaking;
    private int frames;

    public OrdinaryBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setBreakable(true);
        setEmpty(true);

        setAnimation();
        breaking = false;
        frames = animation.getLeftFrames().length;
    }

    private void setAnimation() {
        ImageLoader imageLoader = AssetManager.getInstance().getImageLoader();
        BufferedImage[] leftFrames = imageLoader.getBrickFrames();
        animation = new Animation(leftFrames, leftFrames);
    }

    /**
     * Method ID: MTH-001
     * Triggers break animation if Mario is not SMALL. Adds brick to revealedBricks for animation tracking.
     * @param controller GameController for MapManager access.
     * @return Always null; break animation is self-contained.
     */
    @Override
    public Prize reveal(GameController controller) {
        MapManager manager = controller.getMapManager();
        if (!manager.getMario().isSuper())
            return null;

        breaking = true;
        manager.addRevealedBrick(this);

        double newX = getX() - GameConstants.BRICK_BREAK_OFFSET, newY = getY() - GameConstants.BRICK_BREAK_OFFSET;
        setLocation(newX, newY);

        return null;
    }

    public int getFrames() {
        return frames;
    }

    /**
     * Method ID: MTH-002
     * Steps through break animation frames. When frames reaches -1, brick is removed.
     */
    public void animate() {
        if (breaking) {
            setStyle(animation.animate(GameConstants.BRICK_ANIMATION_SPEED, true));
            frames--;
        }
    }
}
