package utilities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class Config {
    private int soundVolume = 80;
    private boolean muted = false;
    private final Dimension gameResolution;
    private final Random randomGenerator = new Random();

    public Config(Dimension gameResolution) {
        this.gameResolution = gameResolution;
    }

    public Dimension getGameResolution() {
        return gameResolution;
    }

    public Dimension getScreenResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public final int getGameWidth() {
        return gameResolution.width;
    }

    public final int getGameHeight() {
        return gameResolution.height;
    }

    public final Point2D.Float getGameCenterPosition() {
        return new Point2D.Float(getGameWidth() / 2, getGameHeight() / 2);
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

    public float getGlobalVolume() {
        return (float) getSoundVolume() / 100.0f;
    }

    public static boolean isDebugMode() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
    }
}
