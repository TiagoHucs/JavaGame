package utilities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Config {
    private final Dimension screenSize, gameSize;
    private final Point2D.Double scale;
    private final boolean debugMode;
    private boolean muted = false;
    private int soundVolume = 80;
    private final Random randomGenerator = new Random();

    public Config(Dimension screenSize, boolean debugMode) {

        this.debugMode = debugMode;
        this.gameSize = calculateGameSize();

        if (this.debugMode) {
            this.screenSize = gameSize;
        } else {
            this.screenSize = screenSize;
        }

        this.scale = calculateScale();

        System.setProperty("sun.java2d.noddraw", Boolean.TRUE.toString());
        System.setProperty("sun.java2d.opengl", Boolean.TRUE.toString());
    }

    private Point2D.Double calculateScale() {
        return new Point2D.Double((double) screenSize.width / gameSize.width, (double) screenSize.height / gameSize.height);
    }

    private Dimension calculateGameSize() {

        // Numeros mágicos baseados no tamanho da imagem do jogador
        Dimension playerSize = new Dimension(48, 58);

        // Quantidade de tiles por eixo
        Dimension tileCount = new Dimension(20, 10);

        return new Dimension(playerSize.width * tileCount.width, playerSize.height * tileCount.height);
    }

    public final double getScaleWidth() {
        return scale.x;
    }

    public final double getScaleHeight() {
        return scale.y;
    }

    public final int getGameWidth() {
        return gameSize.width;
    }

    public final int getGameHeight() {
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

        gameWindow.setBackground(Color.BLACK);
        gameWindow.setSize(screenSize);
        gameWindow.setMinimumSize(screenSize);
        gameWindow.setMaximumSize(screenSize);

        hideMouseCursor(gameWindow);
    }

    private static void hideMouseCursor(Component gameWindow) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0, 0);
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");
        gameWindow.setCursor(invisibleCursor);
    }

    public float getGlobalVolume() {
        return (float) getSoundVolume() / 100.0f;
    }

    public void applyRenderScale(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g2d.scale(getScaleWidth(), getScaleHeight());
    }
}
