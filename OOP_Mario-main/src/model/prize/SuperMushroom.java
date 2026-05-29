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
 * Component ID: CLS-26
 * Purpose: Collectible that upgrades SMALL Mario to SUPER Mario on contact.
 * Owner: Member 4
 * Ref UML: OD, UC06
 * Derivation: Triggers MarioForm change to SUPER and posts SUPER_MUSHROOM event.
 */
public class SuperMushroom extends BoostItem {

    public SuperMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(GameConstants.SUPER_MUSHROOM_POINTS);
    }

    /**
     * Method ID: MTH-001
     * Upgrades Mario to SUPER form with larger dimensions.
     */
    @Override
    public void onTouch(Mario mario, GameController controller) {
        mario.acquirePoints(getPoint());

        ImageLoader imageLoader = AssetManager.getInstance().getImageLoader();

        if (!mario.getMarioForm().isSuper()) {
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SUPER);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SUPER);

            Animation animation = new Animation(leftFrames, rightFrames);
            MarioForm newForm = new MarioForm(animation, true, false);
            mario.setMarioForm(newForm);
            mario.setDimension(GameConstants.SUPER_MARIO_WIDTH, GameConstants.SUPER_MARIO_HEIGHT);

            EventBus.getInstance().post(GameEvent.SUPER_MUSHROOM);
        }
    }
}
