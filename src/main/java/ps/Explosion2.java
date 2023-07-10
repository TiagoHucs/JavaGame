package ps;

import entities.Ator;
import game.GameComponent;
import lombok.SneakyThrows;
import utilities.GameUtil;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Explosion2 {
    List<BufferedImage> sprites = new ArrayList<>(8);

    Ator ator = null;

    int index = 0;
    List<String> srcList = new ArrayList<>(8);

    @SneakyThrows
    public Explosion2(Ator ator) {
        this.init(ator);
        this.ator = ator;

        for (int i = 1 ; i <= 8; i++) {
            srcList.add("/image/Explosion/explosion-" + i + ".png");
        }

        for (String src : srcList) {
            sprites.add(ResourceManager.get().getImage(src,
                    ator.getLargura(),
                    ator.getAltura()));
        }
    }

    @SneakyThrows
    public void init(Ator ator) {

    }

    public void update(Graphics g, GameComponent game) {
        if(index < 8){
            g.drawImage(sprites.get(index),ator.getX(),ator.getY(),game);
        }
        index++;
    }

    public boolean isFinished() {
        return index > 8;
    }

}
