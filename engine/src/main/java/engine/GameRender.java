package engine;

import game.GameComponent;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameRender extends Canvas {
    private Point2D.Double scale;
    private final GameWindow gameWindow;

    public GameRender(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        setSize(gameWindow.getSize());
        setMinimumSize(gameWindow.getSize());
        setMaximumSize(gameWindow.getSize());

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

        gameWindow.paint(graphics);
    }


}
