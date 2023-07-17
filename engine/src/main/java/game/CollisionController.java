package game;

import entities.GameObject;

public class CollisionController {
    private final Colisor boxCollider = new Colisor();

    public boolean detectaColisao(GameObject a, GameObject b) {
        return boxCollider.detectaColisao(a, b);
    }
}
