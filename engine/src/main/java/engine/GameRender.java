package engine;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameRender {
    private Point2D.Double scale;
    private final GameWindow gameWindow;
    private BufferStrategy bufferStrategy;
    private BufferedImage screenBuffer;

    private boolean manualRender = false;

    public GameRender(GameWindow gameWindow, Dimension viewport) {
        this.gameWindow = gameWindow;
        this.scale = calculateScale(gameWindow, viewport);
    }

    private Point2D.Double calculateScale(GameWindow gameWindow, Dimension viewport) {
        Dimension screenSize = gameWindow.getSize();
        return new Point2D.Double((double) screenSize.width / viewport.width, (double) screenSize.height / viewport.height);
    }

    public void render() {

        if (bufferStrategy == null) {

            bufferStrategy = gameWindow.getBufferStrategy();

            if (manualRender) {

                screenBuffer = GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration()
                        .createCompatibleImage(gameWindow.getWidth(), gameWindow.getHeight());
            }

        }

        renderBufferStrategy();

        if (isVsync()) {
            Toolkit.getDefaultToolkit().sync();
        }

    }

    public void renderBufferStrategy() {

        do {

            do {

                if (manualRender) {

                    Graphics2D graphics = screenBuffer.createGraphics();
                    render(graphics);

                    Graphics finalBuffer = bufferStrategy.getDrawGraphics();
                    finalBuffer.drawImage(screenBuffer, 0, 0, null);
                    finalBuffer.dispose();

                } else {

                    Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                    render(graphics);

                }

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

    public boolean isVsync() {
        return gameWindow.getConfig().isVsync();
    }

}
