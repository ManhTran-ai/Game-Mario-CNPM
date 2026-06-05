package view;

import model.GameObject;
import model.Map;
import model.brick.Brick;
import model.hero.Mario;
import model.Enemy.Enemy;
import model.hero.Fireball;
import model.prize.Prize;
import model.EndFlag;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapRenderer {

    public void render(Map map, Graphics2D g2, Graphics2D cameraG2) {
        renderBackground(map, g2);
        renderPrizes(map, cameraG2);
        renderBricks(map, cameraG2);
        renderEnemies(map, cameraG2);
        renderFireballs(map, cameraG2);
        renderMario(map, cameraG2);
        renderEndFlag(map, cameraG2);
    }

    private void renderBackground(Map map, Graphics2D g2) {
        BufferedImage bg = map.getBackgroundImage();
        if (bg != null) {
            g2.drawImage(bg, 0, 0, null);
        }
    }

    private void renderBricks(Map map, Graphics2D g2) {
        for (Brick brick : map.getBricks()) {
            if (brick != null) {
                renderGameObject(brick, g2);
            }
        }
        for (Brick brick : map.getGroundBricks()) {
            renderGameObject(brick, g2);
        }
    }

    private void renderEnemies(Map map, Graphics2D g2) {
        for (Enemy enemy : map.getEnemies()) {
            if (enemy != null) {
                renderGameObject(enemy, g2);
            }
        }
    }

    private void renderFireballs(Map map, Graphics2D g2) {
        for (Fireball fireball : map.getFireballs()) {
            renderGameObject(fireball, g2);
        }
    }

    private void renderMario(Map map, Graphics2D g2) {
        Mario mario = map.getMario();
        if (mario != null) {
            mario.draw(g2);
        }
    }

    private void renderEndFlag(Map map, Graphics2D g2) {
        EndFlag flag = map.getEndPoint();
        if (flag != null) {
            renderGameObject(flag, g2);
        }
    }

    private void renderGameObject(GameObject obj, Graphics2D g2) {
        BufferedImage style = obj.getStyle();
        if (style != null) {
            g2.drawImage(style, (int) obj.getX(), (int) obj.getY(), null);
        }
    }

    private void renderPrizes(Map map, Graphics2D g2) {
        for (Prize prize : map.getRevealedPrizes()) {
            BufferedImage style = prize.getStyle();
            if (style != null && prize.isDrawable()) {
                g2.drawImage(style, (int) prize.getX(), (int) prize.getY(), null);
            }
        }
    }
}
