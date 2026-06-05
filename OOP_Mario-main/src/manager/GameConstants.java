package manager;

public final class GameConstants {

    private GameConstants() {}

    // Physics
    public static final double GRAVITY = 0.38;
    public static final double MARIO_JUMP_VELOCITY = 10.0;
    public static final double MARIO_MOVE_SPEED = 5.0;
    public static final double ENEMY_MOVE_SPEED = 10.0;
    public static final double FIREBALL_SPEED = 10.0;

    // Mario sizes
    public static final int SMALL_MARIO_WIDTH = 48;
    public static final int SMALL_MARIO_HEIGHT = 38;
    public static final int SUPER_MARIO_WIDTH = 48;
    public static final int SUPER_MARIO_HEIGHT = 96;

    // Sizes
    public static final int BRICK_SIZE = 48;
    public static final int PIPE_SIZE = 96;
    public static final int FIREBALL_SIZE = 24;
    public static final int COIN_WIDTH = 30;
    public static final int COIN_HEIGHT = 42;

    // Points
    public static final int STOMP_POINTS = 100;
    public static final int SUPER_MUSHROOM_POINTS = 500;
    public static final int FIRE_FLOWER_POINTS = 150;
    public static final int ONEUP_MUSHROOM_POINTS = 200;

    // UI
    public static final int SCREEN_WIDTH = 1268;
    public static final int SCREEN_HEIGHT = 708;

    // Map
    public static final double BOTTOM_BORDER_OFFSET = 96.0;
    public static final double CAMERA_THRESHOLD = 600.0;
    public static final double PRIZE_REVEAL_OFFSET = 48.0;
    public static final double BRICK_BREAK_OFFSET = 27.0;

    // Sound
    public static final float DEFAULT_VOLUME = -40.0f;
    public static final float MIN_VOLUME = -80.0f;
    public static final float MAX_VOLUME = 6.0f;

    // Animation
    public static final int ANIMATION_SPEED = 5;
    public static final int BRICK_ANIMATION_SPEED = 3;
    public static final int COIN_REVEAL_PIXELS = 5;

    // Camera
    public static final int CAMERA_SHAKE_FRAMES = 60;
    public static final int CAMERA_SHAKE_INTENSITY = 4;
}
