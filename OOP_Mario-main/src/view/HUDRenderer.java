package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUDRenderer {

    private final Font hudFont;
    private final BufferedImage heartIcon;
    private final BufferedImage coinIcon;
    private final int screenWidth;
    private final int screenHeight;

    public HUDRenderer(Font hudFont, BufferedImage heartIcon, BufferedImage coinIcon,
                       int screenWidth, int screenHeight) {
        this.hudFont = hudFont;
        this.heartIcon = heartIcon;
        this.coinIcon = coinIcon;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void render(Graphics2D g2, int score, int lives, int coins, int time) {
        drawRemainingLives(g2, lives);
        drawPoints(g2, score);
        drawAcquiredCoins(g2, coins);
        drawRemainingTime(g2, time);
    }

    private void drawRemainingTime(Graphics2D g2, int time) {
        g2.setFont(hudFont.deriveFont(25f));
        g2.setColor(Color.WHITE);
        String displayedStr = "TIME: " + time;
        g2.drawString(displayedStr, screenWidth - 150, 50);
    }

    private void drawAcquiredCoins(Graphics2D g2, int coins) {
        g2.setFont(hudFont.deriveFont(30f));
        g2.setColor(Color.WHITE);
        String displayedStr = "" + coins;
        g2.drawImage(coinIcon, screenWidth - 115, 10, null);
        g2.drawString(displayedStr, screenWidth - 65, 50);
    }

    private void drawRemainingLives(Graphics2D g2, int lives) {
        g2.setFont(hudFont.deriveFont(30f));
        g2.setColor(Color.WHITE);
        String displayedStr = "" + lives;
        g2.drawImage(heartIcon, 50, 10, null);
        g2.drawString(displayedStr, 100, 50);
    }

    private void drawPoints(Graphics2D g2, int score) {
        g2.setFont(hudFont.deriveFont(25f));
        g2.setColor(Color.WHITE);
        String displayedStr = "Points: " + score;
        g2.drawString(displayedStr, 300, 50);
    }

    public void drawVictoryScreen(Graphics2D g2, int score, int coins) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setFont(hudFont.deriveFont(50f));
        g2.setColor(Color.YELLOW);
        String displayedStr = "YOU WON!";
        int stringLength = g2.getFontMetrics().stringWidth(displayedStr);
        g2.drawString(displayedStr, (screenWidth - stringLength) / 2, screenHeight / 2 - 40);

        g2.setFont(hudFont.deriveFont(25f));
        g2.setColor(Color.WHITE);
        String scoreStr = "Score: " + score;
        int scoreLength = g2.getFontMetrics().stringWidth(scoreStr);
        g2.drawString(scoreStr, (screenWidth - scoreLength) / 2, screenHeight / 2 + 20);

        String coinsStr = "Coins: " + coins;
        int coinsLength = g2.getFontMetrics().stringWidth(coinsStr);
        g2.drawString(coinsStr, (screenWidth - coinsLength) / 2, screenHeight / 2 + 55);

        String hint = "Press ESC to return to menu";
        int hintLength = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, (screenWidth - hintLength) / 2, screenHeight / 2 + 100);
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setFont(hudFont.deriveFont(50f));
        g2.setColor(Color.WHITE);
        String displayedStr = "PAUSED";
        int stringLength = g2.getFontMetrics().stringWidth(displayedStr);
        g2.drawString(displayedStr, (screenWidth - stringLength) / 2, screenHeight / 2 - 30);

        g2.setFont(hudFont.deriveFont(20f));
        String hint = "Press ESC to resume";
        int hintLength = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, (screenWidth - hintLength) / 2, screenHeight / 2 + 20);
    }

    public void drawGameOverScreen(Graphics2D g2, int score, int coins) {
        g2.setFont(hudFont.deriveFont(50f));
        g2.setColor(new Color(130, 48, 48));
        String acquiredPoints = "Score: " + score;
        int stringLength = g2.getFontMetrics().stringWidth(acquiredPoints);
        int stringHeight = g2.getFontMetrics().getHeight();
        g2.drawString(acquiredPoints, (screenWidth - stringLength) / 2, screenHeight - stringHeight * 2);

        g2.setFont(hudFont.deriveFont(20f));
        g2.setColor(Color.WHITE);
        String coinsStr = "Coins collected: " + coins;
        int coinsLength = g2.getFontMetrics().stringWidth(coinsStr);
        g2.drawString(coinsStr, (screenWidth - coinsLength) / 2, screenHeight - stringHeight);

        String hint = "Press ESC to return to menu";
        int hintLength = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, (screenWidth - hintLength) / 2, screenHeight / 2 + 50);
    }
}
