package pong;

import entities.Actor;
import game.GameComponent;
import utilities.Config;

import java.awt.*;
import java.awt.geom.Point2D;

public class PongGameComponent extends GameComponent {

    private Actor ball = new Actor();
    private float f = 5.0f;
    private float x = f;
    private float y = f;


    public PongGameComponent(Config cfg) {
        super(cfg);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getCfg().getGameWidth(),getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(ball.getPositionWithOffsetX(),ball.getPositionWithOffsetY(),10,10);
    }

    @Override
    public void update() {
        ball.setVelocity(new Point2D.Float(x, y));
        ball.move();
        if(ball.getPosition().getY() > getCfg().getGameHeight()){
            y = -f;
        } else if(ball.getPosition().getY() < 0){
            y = f;
        }
        if(ball.getPosition().getX() > getCfg().getGameWidth()){
            x = -f;
        } else if(ball.getPosition().getX() < 0){
            x = f;
        }
    }
}
