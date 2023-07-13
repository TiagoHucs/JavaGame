package utilities;

import java.awt.*;
import java.util.Random;

public class Config {
    private final Dimension screenSize, gameSize;
    private boolean muted = false;
    private int soundVolume = 80;
    private final Random randomGenerator = new Random();

    public Config(Dimension screenSize) {
        this.screenSize = screenSize;
        this.gameSize = new Dimension(screenSize.width, screenSize.height);
        System.setProperty("sun.java2d.noddraw", Boolean.TRUE.toString());
        System.setProperty("sun.java2d.opengl", Boolean.TRUE.toString());
    }

    public int getGameWidth() {
        return gameSize.width;
    }

    public int getGameHeight() {
        return gameSize.height;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = Math.max(0, Math.min(100, soundVolume));
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setup(Component gameWindow) {

        if (gameWindow instanceof Frame) {
            ((Frame) gameWindow).setUndecorated(true);
        }

        gameWindow.setBackground(Color.BLACK);
        gameWindow.setSize(screenSize);
        gameWindow.setMaximumSize(screenSize);
        gameWindow.setMinimumSize(gameSize);
        gameWindow.requestFocusInWindow();
    }

    public float getGlobalVolume() {
        return (float) getSoundVolume() / 100.0f;
    }
}
