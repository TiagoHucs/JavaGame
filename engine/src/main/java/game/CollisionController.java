package game;

import entities.Actor;

public class CollisionController {
    private final Colisor boxCollider = new Colisor();

    public boolean detectaColisao(Actor a, Actor b) {
        return boxCollider.detectaColisao(a, b);
    }
}
