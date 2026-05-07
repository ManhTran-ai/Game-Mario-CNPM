package view;

import manager.GameStatus;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UIManager extends JPanel {

    private GameController controller;
    private Font gameFont;
    private BufferedImage startScreenImage, aboutScreenImage, helpScreenImage, gameOverScreen;
    private BufferedImage selectIcon;
    private MapSelection mapSelection;
    private HUDRenderer hudRenderer;

    public UIManager(GameController controller, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        this.controller = controller;
        ImageLoader loader = controller.getImageLoader();

        mapSelection = new MapSelection();

        BufferedImage sprite = loader.loadImage("/sprite.png");
        BufferedImage heartIcon = loader.loadImage("/heart-icon.png");
        BufferedImage coinIcon = loader.getSubImage(sprite, 1, 5, 48, 48);
        this.selectIcon = loader.loadImage("/select-icon.png");
        this.startScreenImage = loader.loadImage("/start-screen.png");
        this.helpScreenImage = loader.loadImage("/help-screen.png");
        this.aboutScreenImage = loader.loadImage("/about-screen.png");
        this.gameOverScreen = loader.loadImage("/game-over.png");

        try {
            InputStream in = getClass().getResourceAsStream("/media/font/mario-font.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.BOLD, 18);
            e.printStackTrace();
        }

        hudRenderer = new HUDRenderer(gameFont, heartIcon, coinIcon, width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        GameStatus gameStatus = controller.getGameStatus();

        if (gameStatus == GameStatus.START_SCREEN) {
            drawStartScreen(g2);
        } else if (gameStatus == GameStatus.MAP_SELECTION) {
            drawMapSelectionScreen(g2);
        } else if (gameStatus == GameStatus.ABOUT_SCREEN) {
            drawAboutScreen(g2);
        } else if (gameStatus == GameStatus.HELP_SCREEN) {
            drawHelpScreen(g2);
        } else if (gameStatus == GameStatus.GAME_OVER) {
            drawGameOverScreen(g2);
        } else if (gameStatus == GameStatus.RUNNING || gameStatus == GameStatus.PAUSED) {
            drawGameView(g2);
            if (gameStatus == GameStatus.PAUSED) {
                hudRenderer.drawPauseScreen(g2);
            }
        } else if (gameStatus == GameStatus.MISSION_PASSED) {
            drawGameView(g2);
            hudRenderer.drawVictoryScreen(g2, controller.getScore(), controller.getCoins());
        }
    }

    private void drawGameView(Graphics2D g2) {
        Point camLocation = controller.getCameraLocation();

        Graphics2D cameraG2 = (Graphics2D) g2.create();
        cameraG2.translate(-camLocation.x, -camLocation.y);
        controller.drawMap(g2, cameraG2);
        cameraG2.dispose();

        hudRenderer.render(g2, controller.getScore(), controller.getRemainingLives(),
                          controller.getCoins(), controller.getRemainingTime());
    }

    private void drawHelpScreen(Graphics2D g2) {
        g2.drawImage(helpScreenImage, 0, 0, null);
    }

    private void drawAboutScreen(Graphics2D g2) {
        g2.drawImage(aboutScreenImage, 0, 0, null);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.drawImage(gameOverScreen, 0, 0, null);
        hudRenderer.drawGameOverScreen(g2, controller.getScore(), controller.getCoins());
    }

    private void drawStartScreen(Graphics2D g2) {
        int row = controller.getStartScreenSelection().getLineNumber();
        g2.drawImage(startScreenImage, 0, 0, null);
        g2.drawImage(selectIcon, 375, row * 70 + 440, null);
    }

    private void drawMapSelectionScreen(Graphics2D g2) {
        g2.setFont(gameFont.deriveFont(50f));
        g2.setColor(Color.WHITE);
        mapSelection.draw(g2);
        int row = controller.getSelectedMap();
        int yLocation = row * 100 + 300 - selectIcon.getHeight();
        g2.drawImage(selectIcon, 375, yLocation, null);
    }

    public String selectMapViaMouse(Point mouseLocation) {
        return mapSelection.selectMap(mouseLocation);
    }

    public String selectMapViaKeyboard(int index) {
        return mapSelection.selectMap(index);
    }

    public int changeSelectedMap(int index, boolean up) {
        return mapSelection.changeSelectedMap(index, up);
    }
}
