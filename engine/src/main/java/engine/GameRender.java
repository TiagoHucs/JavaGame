package engine;

import game.GameComponent;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameRender extends Canvas {
    public static final float FPS = 60.0f;
    private Point2D.Double scale;
    private final GameComponent game;

    public GameRender(GameWindow gameWindow, GameComponent game) {
        this.game = game;

        setSize(gameWindow.getSize());
        setMinimumSize(gameWindow.getSize());
        setMaximumSize(gameWindow.getSize());

        // Forçar acelerção via Hardware
        System.setProperty("sun.java2d.noddraw", Boolean.TRUE.toString());
        System.setProperty("sun.java2d.opengl", Boolean.TRUE.toString());

        // Escala do Render
        this.scale = calculateScale(gameWindow);
    }

    private Point2D.Double calculateScale(GameWindow gameWindow) {
        Dimension screenSize = gameWindow.getSize();
        Dimension gameSize = gameWindow.getGameSize();
        return new Point2D.Double((double) screenSize.width / gameSize.width, (double) screenSize.height / gameSize.height);
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        graphics.scale(scale.x, scale.y);

        game.draw(graphics);
    }


}
