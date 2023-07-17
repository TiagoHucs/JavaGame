package engine;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;

public class GameRender {
    private Point2D.Double scale;
    private final GameWindow gameWindow;
    private BufferStrategy bufferStrategy;

    public GameRender(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.scale = calculateScale(gameWindow);
    }

    private Point2D.Double calculateScale(GameWindow gameWindow) {
        Dimension screenSize = gameWindow.getSize();
        Dimension gameSize = gameWindow.getGameSize();
        return new Point2D.Double((double) screenSize.width / gameSize.width, (double) screenSize.height / gameSize.height);
    }

    public void render() {

        if (bufferStrategy == null)
            bufferStrategy = gameWindow.getBufferStrategy();

        renderBufferStrategy();
    }

    public void renderBufferStrategy() {

        do {

            do {
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                render(graphics);
            } while (bufferStrategy.contentsRestored());

            bufferStrategy.show();

        } while (bufferStrategy.contentsLost());

    }

    public void render(Graphics2D graphics) {

        try {

            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
            graphics.scale(scale.x, scale.y);

            gameWindow.paint(graphics);

        } finally {
            graphics.dispose();
        }

    }
}
