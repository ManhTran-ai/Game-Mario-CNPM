package controller;

import event.EventBus;
import event.GameEvent;
import manager.*;
import model.hero.Mario;
import view.GameWindow;
import view.ImageLoader;
import view.StartScreenSelection;
import view.UIManager;

import javax.swing.*;
import java.awt.*;

/**
 * Component ID: CLS-01
 * Purpose: Main entry point and game loop orchestrator. Manages game state transitions, rendering, input routing, and coordinate subsystems (MapManager, UIManager, SoundManager, Camera).
 * Owner: Member 1
 * Ref UML: CD, AD01, UC01, UC02, UC03, UC04, UC05, UC06, UC07, UC08, UC09
 * Derivation: Central facade pattern combining AD01 Game Loop flow with AD02 Event system.
 */
public class GameController implements Runnable {

    private MapManager mapManager;
    private UIManager uiManager;
    private SoundManager soundManager;
    private GameStatus gameStatus;
    private boolean isRunning;
    private Camera camera;
    private ImageLoader imageLoader;
    private Thread thread;
    private GameWindow gameWindow;
    private StartScreenSelection startScreenSelection = StartScreenSelection.START_GAME;
    private int selectedMap = 0;

    /**
     * Method ID: MTH-001
     * Initializes all subsystems and starts the game thread. Ref: AD01 entry point.
     */
    private GameController() {
        init();
    }

    private void init() {
        imageLoader = new ImageLoader();
        InputManager inputManager = new InputManager(this);
        gameStatus = GameStatus.START_SCREEN;
        camera = new Camera();
        uiManager = new UIManager(this, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        soundManager = new SoundManager();
        mapManager = new MapManager();

        soundManager.setVolume(GameConstants.DEFAULT_VOLUME);
        registerEventHandlers();

        gameWindow = new GameWindow(uiManager, inputManager);

        start();
    }

    private synchronized void start() {
        if (isRunning)
            return;

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void reset() {
        resetCamera();
        setGameStatus(GameStatus.START_SCREEN);
    }

    public void resetCamera() {
        camera = new Camera();
        soundManager.restartBackground();
    }

    public void selectMapViaMouse() {
        String path = uiManager.selectMapViaMouse(uiManager.getMousePosition());
        if (path != null) {
            createMap(path);
        }
    }

    public void selectMapViaKeyboard() {
        String path = uiManager.selectMapViaKeyboard(selectedMap);
        if (path != null) {
            createMap(path);
        }
    }

    public void changeSelectedMap(boolean up) {
        selectedMap = uiManager.changeSelectedMap(selectedMap, up);
    }

    private void createMap(String path) {
        boolean loaded = mapManager.createMap(imageLoader, path);
        if (loaded) {
            setGameStatus(GameStatus.RUNNING);
            soundManager.restartBackground();
        } else {
            setGameStatus(GameStatus.START_SCREEN);
        }
    }

    /**
     * Method ID: MTH-002
     * Main game loop at fixed 60 ticks per second. Ref: AD01 Game Loop diagram.
     * @see Runnable#run()
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (isRunning && !thread.isInterrupted()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (gameStatus == GameStatus.RUNNING) {
                    gameLoop();
                }
                delta--;
            }
            render();

            if (gameStatus != GameStatus.RUNNING) {
                timer = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                mapManager.updateTime();
            }
        }
    }

    private void render() {
        uiManager.repaint();
    }

    /**
     * Method ID: MTH-003
     * Core game logic tick: update positions, check collisions, update camera, evaluate end conditions. Ref: AD01.
     */
    private void gameLoop() {
        updateLocations();
        checkCollisions();
        updateCamera();

        if (isGameOver()) {
            setGameStatus(GameStatus.GAME_OVER);
        }

        int missionPassed = passMission();
        if (missionPassed > -1) {
            mapManager.acquirePoints(missionPassed);
        } else if (mapManager.endLevel())
            setGameStatus(GameStatus.MISSION_PASSED);
    }

    /**
     * Method ID: MTH-004
     * Shifts camera rightward when Mario moves past threshold. Mario leftward movement is clamped by the left boundary.
     */
    private void updateCamera() {
        Mario mario = mapManager.getMario();
        double marioVelocityX = mario.getVelX();
        double shiftAmount = 0;

        if (marioVelocityX > 0 && mario.getX() - GameConstants.CAMERA_THRESHOLD > camera.getX()) {
            shiftAmount = marioVelocityX;
        }

        camera.moveCam(shiftAmount, 0);
    }

    private void updateLocations() {
        mapManager.updateLocations();
    }

    private void checkCollisions() {
        mapManager.checkCollisions(this);
    }

    /**
     * Method ID: MTH-005
     * Routes input to appropriate game logic based on current GameStatus. Ref: AD01, UC01-UC09.
     * @param input The ButtonAction triggered by InputManager.
     */
    public void receiveInput(ButtonAction input) {

        if (gameStatus == GameStatus.START_SCREEN) {
            if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.START_GAME) {
                startGame();
            } else if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.VIEW_ABOUT) {
                setGameStatus(GameStatus.ABOUT_SCREEN);
            } else if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.VIEW_HELP) {
                setGameStatus(GameStatus.HELP_SCREEN);
            } else if (input == ButtonAction.GO_UP) {
                selectOption(true);
            } else if (input == ButtonAction.GO_DOWN) {
                selectOption(false);
            }
        } else if (gameStatus == GameStatus.MAP_SELECTION) {
            if (input == ButtonAction.SELECT) {
                selectMapViaKeyboard();
            } else if (input == ButtonAction.GO_UP) {
                changeSelectedMap(true);
            } else if (input == ButtonAction.GO_DOWN) {
                changeSelectedMap(false);
            }
        } else if (gameStatus == GameStatus.RUNNING) {
            Mario mario = mapManager.getMario();
            if (input == ButtonAction.JUMP) {
                mario.jump();
            } else if (input == ButtonAction.M_RIGHT) {
                mario.move(true, camera);
            } else if (input == ButtonAction.M_LEFT) {
                mario.move(false, camera);
            } else if (input == ButtonAction.ACTION_COMPLETED) {
                mario.setVelX(0);
            } else if (input == ButtonAction.FIRE) {
                mapManager.fire();
            } else if (input == ButtonAction.PAUSE_RESUME) {
                pauseGame();
            }
        } else if (gameStatus == GameStatus.PAUSED) {
            if (input == ButtonAction.PAUSE_RESUME) {
                pauseGame();
            }
        } else if (gameStatus == GameStatus.GAME_OVER && input == ButtonAction.GO_TO_START_SCREEN) {
            reset();
        } else if (gameStatus == GameStatus.MISSION_PASSED && input == ButtonAction.GO_TO_START_SCREEN) {
            reset();
        }

        if (input == ButtonAction.GO_TO_START_SCREEN) {
            setGameStatus(GameStatus.START_SCREEN);
        }
    }

    private void selectOption(boolean selectUp) {
        startScreenSelection = startScreenSelection.select(selectUp);
    }

    private void startGame() {
        if (gameStatus != GameStatus.GAME_OVER) {
            setGameStatus(GameStatus.MAP_SELECTION);
        }
    }

    /**
     * Method ID: MTH-006
     * Toggles between RUNNING and PAUSED states, pausing/resuming background music.
     */
    private void pauseGame() {
        if (gameStatus == GameStatus.RUNNING) {
            setGameStatus(GameStatus.PAUSED);
            soundManager.pauseBackground();
        } else if (gameStatus == GameStatus.PAUSED) {
            setGameStatus(GameStatus.RUNNING);
            soundManager.resumeBackground();
        }
    }

    public void shakeCamera() {
        camera.shakeCamera();
    }

    public void resetCurrentMap() {
        mapManager.resetCurrentMap(this);
    }

    private boolean isGameOver() {
        if (gameStatus == GameStatus.RUNNING)
            return mapManager.isGameOver();
        return false;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public StartScreenSelection getStartScreenSelection() {
        return startScreenSelection;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore() {
        return mapManager.getScore();
    }

    public int getRemainingLives() {
        return mapManager.getRemainingLives();
    }

    public int getCoins() {
        return mapManager.getCoins();
    }

    public int getSelectedMap() {
        return selectedMap;
    }

    public void drawMap(Graphics2D g2, Graphics2D cameraG2) {
        mapManager.drawMap(g2, cameraG2);
    }

    public Point getCameraLocation() {
        return new Point((int) camera.getX(), (int) camera.getY());
    }

    private int passMission() {
        return mapManager.passMission();
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * Method ID: MTH-007
     * Wires GameEvents to their corresponding SoundManager playback methods. Ref: AD02.
     */
    private void registerEventHandlers() {
        EventBus bus = EventBus.getInstance();
        bus.subscribe(GameEvent.JUMP, e -> soundManager.playJump());
        bus.subscribe(GameEvent.COIN_COLLECTED, e -> soundManager.playCoin());
        bus.subscribe(GameEvent.FIREBALL_FIRED, e -> soundManager.playFireball());
        bus.subscribe(GameEvent.STOMP_ENEMY, e -> soundManager.playStomp());
        bus.subscribe(GameEvent.ONE_UP, e -> soundManager.playOneUp());
        bus.subscribe(GameEvent.SUPER_MUSHROOM, e -> soundManager.playSuperMushroom());
        bus.subscribe(GameEvent.FIRE_FLOWER, e -> soundManager.playFireFlower());
        bus.subscribe(GameEvent.MARIO_DIES, e -> {
            soundManager.playMarioDies();
            mapManager.getMario().setRemainingLives(mapManager.getMario().getRemainingLives() - 1);
        });
        bus.subscribe(GameEvent.CAMERA_SHAKE, e -> shakeCamera());
    }

    public static void main(String... args) {
        new GameController();
    }

    public int getRemainingTime() {
        return mapManager.getRemainingTime();
    }
}