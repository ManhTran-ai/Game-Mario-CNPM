package model.brick;

import model.prize.Prize;
import view.ImageLoader;

import controller.GameController;
import java.awt.image.BufferedImage;

/**
 * Component ID: CLS-20
 * Purpose: Brick containing a hidden Prize. On first hit by Mario, reveals the prize and changes to an empty brick sprite.
 * Owner: Member 4
 * Ref UML: OD, UC05
 * Derivation: Non-breakable brick type that yields a single prize via generateRandomPrize.
 */
public class SurpriseBrick extends Brick {

    private Prize prize;

    public SurpriseBrick(double x, double y, BufferedImage style, Prize prize) {
        super(x, y, style);
        setBreakable(false);
        setEmpty(false);
        this.prize = prize;
    }

    /**
     * Method ID: MTH-001
     * Reveals the contained prize and changes sprite to empty brick.
     * @param controller GameController for accessing ImageLoader.
     * @return The contained Prize to add to the map.
     */
    @Override
    public Prize reveal(GameController controller) {
        BufferedImage newStyle = controller.getImageLoader().loadImage("/sprite.png");
        newStyle = controller.getImageLoader().getSubImage(newStyle, 1, 2, 48, 48);

        if (prize != null) {
            prize.reveal();
        }

        setEmpty(true);
        setStyle(newStyle);

        Prize toReturn = this.prize;
        this.prize = null;
        return toReturn;
    }

    @Override
    public Prize getPrize() {
        return prize;
    }
}
