package model.prize;

import event.EventBus;
import event.GameEvent;
import manager.GameConstants;
import model.hero.Mario;
import model.hero.MarioForm;
import view.Animation;
import view.AssetManager;
import view.ImageLoader;

import controller.GameController;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-27
 * Purpose: Collectible that upgrades Mario to FIRE form on contact, enabling fireball attacks.
 * Owner: Member 4
 * Ref UML: OD, UC06, UC07
 * Derivation: Triggers MarioForm change to FIRE; stays at brick level (no vertical movement).
 */
public class FireFlower extends BoostItem {

    public FireFlower(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(GameConstants.FIRE_FLOWER_POINTS);
    }

    /**
     * Method ID: MTH-001
     * Upgrades Mario to FIRE form with fireball firing capability.
     */
    @Override
    public void onTouch(Mario mario, GameController controller) {
        mario.acquirePoints(getPoint());

        ImageLoader imageLoader = AssetManager.getInstance().getImageLoader();

        if (!mario.getMarioForm().isFire()) {
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.FIRE);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.FIRE);

            Animation animation = new Animation(leftFrames, rightFrames);
            MarioForm newForm = new MarioForm(animation, true, true);
            mario.setMarioForm(newForm);
            mario.setDimension(GameConstants.SUPER_MARIO_WIDTH, GameConstants.SUPER_MARIO_HEIGHT);

            EventBus.getInstance().post(GameEvent.FIRE_FLOWER);
        }
    }

    /**
     * Method ID: MTH-002
     * FireFlower stays at brick height (no vertical movement after reveal).
     */
    @Override
    public void updateLocation() {
    }

}
