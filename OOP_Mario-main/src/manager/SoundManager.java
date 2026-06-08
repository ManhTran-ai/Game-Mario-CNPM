package manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Component ID: CLS-40
 * Purpose: Manages background music playback and one-shot sound effect clips with volume control.
 * Owner: Member 5
 * Ref UML: SD, AD02
 * Derivation: Loaded and triggered via GameEvent subscriptions in GameController.
 */
public class SoundManager {

    private Clip background;
    private long clipTime = 0;
    private FloatControl backgroundControl;
    private List<Clip> activeClips;
    private float currentVolume;

    public SoundManager() {
        activeClips = new ArrayList<>();
        currentVolume = GameConstants.DEFAULT_VOLUME;
        background = getClip(loadAudio("background"));
        if (background != null) {
            backgroundControl = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(currentVolume);
        }
    }

    private AudioInputStream loadAudio(String url) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/media/audio/" + url + ".wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            return AudioSystem.getAudioInputStream(bufferedIn);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private Clip getClip(AudioInputStream stream) {
        if (stream == null) return null;
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            activeClips.add(clip);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method ID: MTH-001
     * Sets volume for background music and all active clips.
     * @param volume Gain value in dB, clamped to MIN_VOLUME..MAX_VOLUME.
     */
    public void setVolume(float volume) {
        float gain = Math.max(GameConstants.MIN_VOLUME, Math.min(GameConstants.MAX_VOLUME, volume));
        currentVolume = gain;

        if (backgroundControl != null) {
            backgroundControl.setValue(gain);
        }

        for (Clip clip : activeClips) {
            try {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                if (control != null) {
                    control.setValue(gain);
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    /**
     * Method ID: MTH-002
     * Closes finished clips to free resources.
     */
    public void cleanupFinishedClips() {
        Iterator<Clip> iterator = activeClips.iterator();
        while (iterator.hasNext()) {
            Clip clip = iterator.next();
            if (!clip.isRunning()) {
                clip.close();
                iterator.remove();
            }
        }
    }

    public void resumeBackground() {
        if (background != null) {
            background.setMicrosecondPosition(clipTime);
            background.start();
        }
    }

    public void pauseBackground() {
        if (background != null) {
            clipTime = background.getMicrosecondPosition();
            background.stop();
        }
    }

    public void restartBackground() {
        clipTime = 0;
        resumeBackground();
    }

    public void playJump() {
        playSound("jump");
    }

    public void playCoin() {
        playSound("coin");
    }

    public void playFireball() {
        playSound("fireball");
    }

    public void playGameOver() {
        playSound("gameOver");
    }

    public void playStomp() {
        playSound("stomp");
    }

    public void playOneUp() {
        playSound("oneUp");
    }

    public void playSuperMushroom() {
        playSound("superMushroom");
    }

    public void playMarioDies() {
        playSound("marioDies");
    }

    public void playFireFlower() {
        playSound("fireFlower");
    }

    private void playSound(String sound) {
        cleanupFinishedClips();
        AudioInputStream audioStream = loadAudio(sound);
        if (audioStream != null) {
            Clip clip = getClip(audioStream);
            if (clip != null) {
                try {
                    FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    if (control != null) {
                        control.setValue(currentVolume);
                    }
                } catch (IllegalArgumentException ignored) {
                }
                clip.start();
            }
        }
    }
}
