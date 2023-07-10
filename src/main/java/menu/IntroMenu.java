package menu;

import game.GameComponent;
import game.GameState;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class IntroMenu extends AbstractGameMenu {

    private Image logo;

    public IntroMenu(GameComponent gameComponent) {
        super(gameComponent);
        try {
            logo = ResourceManager.get().getImage("/image/logo.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        setStartLine();
        drawImage(g,logo);
        write( g,Color.WHITE,"Press any key",20);
    }

    @Override
    public void executeAction(String action) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameComponent.gameState.state = GameState.State.MENU;
    }

    @Override
    public void update(GameComponent gameComponent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
