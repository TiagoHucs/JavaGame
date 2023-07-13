package utilities;

import java.awt.*;
import java.util.Random;

public class Config {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int larguraTela = (int) screenSize.getWidth();
    private final int alturaTela = (int) screenSize.getHeight();
    private final int resolution = (int) screenSize.getWidth();
    private boolean muted = true;
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
        gameWindow.setPreferredSize(screenSize);
        gameWindow.requestFocusInWindow();
    }
}
