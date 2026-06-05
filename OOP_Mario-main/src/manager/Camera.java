package manager;

/**
 * Component ID: CLS-08
 * Purpose: Tracks viewport offset and handles camera shake effect triggered by events.
 * Owner: Member 2
 * Ref UML: CD, SD01
 * Derivation: Camera position drives the world-to-screen transform in UIManager.
 */
public class Camera {

    private double x, y;
    private int frameNumber;
    private boolean shaking;

    public Camera() {
        this.x = 0;
        this.y = 0;
        this.frameNumber = 0;
        this.shaking = false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Method ID: MTH-001
     * Triggers a 60-frame camera shake. Called via CAMERA_SHAKE event.
     */
    public void shakeCamera() {
        shaking = true;
        frameNumber = 60;
    }

    /**
     * Method ID: MTH-002
     * Applies a shift; when shaking, applies an alternating offset instead.
     * @param xAmount Horizontal shift in world units.
     * @param yAmount Vertical shift in world units.
     */
    public void moveCam(double xAmount, double yAmount) {
        if (shaking && frameNumber > 0) {
            int direction = (frameNumber % 2 == 0) ? 1 : -1;
            x = x + GameConstants.CAMERA_SHAKE_INTENSITY * direction;
            frameNumber--;
        } else {
            x = x + xAmount;
            y = y + yAmount;
        }

        if (frameNumber < 0)
            shaking = false;
    }
}
