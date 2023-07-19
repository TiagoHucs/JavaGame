package game;

import entities.GameObject;
import lombok.NonNull;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class CollisionController {
    public interface CollisionEvent {
        /**
         * Evento disparado quando ocorrer uma colisão entre os 2 objetos
         * @param a Primeiro objeto da checagem
         * @param b Segundo objeto da checagem
         * @return Remover a verificação de colisão entre a e b
         */
        boolean onCollision(GameObject a, GameObject b);
    };
    private class CollisionPair {
        protected GameObject a, b;
        protected CollisionEvent event;
        public CollisionPair(GameObject a, GameObject b, CollisionEvent event) {
            this.a = a;
            this.b = b;
            this.event = event;
        }
    };

    private final Colisor boxCollider = new Colisor();

    private LinkedList<CollisionPair> collisionPairs = new LinkedList<CollisionPair>();

    public boolean detectaColisao(GameObject a, GameObject b) {
        return boxCollider.detectaColisao(a, b);
    }

    public void watchForCollision(@NonNull GameObject a, @NonNull GameObject b, @NonNull CollisionEvent event) {
        collisionPairs.add(new CollisionPair(a, b, event));
    }

    public void doBruteForceCheck() {

        Iterator<CollisionPair> iterator = collisionPairs.iterator();

        while (iterator.hasNext()) {

            CollisionPair pair = iterator.next();

            Rectangle aBounds = pair.a.getBounds();
            Rectangle bBounds = pair.b.getBounds();

            if (aBounds.intersects(bBounds) && pair.event.onCollision(pair.a, pair.b)) {
                iterator.remove();
            }
        }
    }
}
