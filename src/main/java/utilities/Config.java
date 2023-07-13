package utilities;

import java.awt.*;
import java.util.Random;

public class Config {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int larguraTela = (int) screenSize.getWidth();
    private final int alturaTela = (int) screenSize.getHeight();
    private final int resolution = (int) screenSize.getWidth();
    private boolean muted = false;
    private int soundVolume = 80;
    private final Random randomGenerator = new Random();
    private final Font font = new Font("TimesRoman", Font.PLAIN, 25);

    public Config() {
    }

    public int getResolution() {
        return this.resolution;
    }

    public int getLarguraTela() {
        return this.larguraTela;
    }

    public int getAlturaTela() {
        return this.alturaTela;
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

    public Font getFont() {
        return font;
    }

    public void setup(Component gameWindow) {
        if (gameWindow instanceof Frame) {
            ((Frame) gameWindow).setUndecorated(true);
        }
        gameWindow.setBackground(Color.BLACK);
        gameWindow.setSize(screenSize);
        gameWindow.setPreferredSize(screenSize);
        gameWindow.requestFocusInWindow();
    }

    public float getGlobalVolume() {
        return (float) getSoundVolume() / 100.0f;
    }
}
