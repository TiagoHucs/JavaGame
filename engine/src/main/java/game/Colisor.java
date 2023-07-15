package game;

import entities.Actor;

public class Colisor {

    public boolean detectaColisao(Actor a, Actor b) {

        float myleft = a.getPosition().x;
        float myright = a.getPosition().x + a.getSize().x;
        float mytop = a.getPosition().y;
        float mybottom = a.getPosition().y + a.getSize().y;

        float otherleft = b.getPosition().x;
        float otherright = b.getPosition().x + b.getSize().y;
        float othertop = b.getPosition().y;
        float otherbottom = b.getPosition().y + b.getSize().y;

        return (mybottom >= othertop) &&
                (mytop <= otherbottom) &&
                (myright >= otherleft) &&
                (myleft <= otherright);
    }
}