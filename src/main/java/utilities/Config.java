package utilities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class Config {
    private final Dimension screenSize, gameSize;
    private final Point2D.Double scale;
    private boolean muted = false;
    private int soundVolume = 80;
    private final Random randomGenerator = new Random();

    private boolean fixedAspectRatio = false;

    public Config(Dimension screenSize) {
        this.screenSize = screenSize;
        this.gameSize = new Dimension(640, 480);
        this.scale = new Point2D.Double((double) screenSize.width / gameSize.width, (double) screenSize.height / gameSize.height);
        //this.fixAspectRatio();
        System.setProperty("sun.java2d.noddraw", Boolean.TRUE.toString());
        System.setProperty("sun.java2d.opengl", Boolean.TRUE.toString());
    }

    // Caso queira a imagem 1x1 sem esticar
    public void fixAspectRatio() {

        if (scale.x < scale.y) {
            scale.y = scale.x;
        } else {
            scale.x = scale.y;
        }

        fixedAspectRatio = true;
    }

    public double getScaleWidth() {
        return scale.x;
    }

    public double getScaleHeight() {
        return scale.y;
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

    public void applyRenderScale(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (fixedAspectRatio) {
            double tx = gameSize.getWidth() * 0.5;
            double ty = 0;
            g2d.translate(tx, ty);
        }

        g2d.scale(scale.x, scale.y);
    }
}
