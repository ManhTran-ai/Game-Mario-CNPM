package model.prize;

import event.EventBus;
import event.GameEvent;
import manager.GameConstants;
import model.hero.Mario;

import controller.GameController;

import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-28
 * Purpose: Collectible that adds one life to Mario's remaining lives on contact.
 * Owner: Member 4
 * Ref UML: OD, UC08
 * Derivation: Awards 1 extra life and posts ONE_UP event.
 */
public class OneUpMushroom extends BoostItem {

    public OneUpMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(GameConstants.ONEUP_MUSHROOM_POINTS);
    }

    /**
     * Method ID: MTH-001
     * Adds one life to Mario's remaining lives.
     */
    @Override
    public void onTouch(Mario mario, GameController controller) {
        mario.acquirePoints(getPoint());
        mario.setRemainingLives(mario.getRemainingLives() + 1);
        EventBus.getInstance().post(GameEvent.ONE_UP);
    }
}
