package view;

import controller.InputManager;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

    private JFrame frame;

    public GameWindow(UIManager uiManager, InputManager inputManager) {
        frame = new JFrame("Super Mario Bros.");
        frame.add(uiManager);
        frame.addKeyListener(inputManager);
        frame.addMouseListener(inputManager);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();
        }
    }
}
