package game;

import entities.Ator;

public class CollisionController {
    private final Colisor boxCollider = new Colisor();

    public boolean detectaColisao(Ator a, Ator b) {
        return boxCollider.detectaColisao(a, b);
    }
}
