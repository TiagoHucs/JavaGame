package game;

import entities.GameObject;
import lombok.NonNull;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class CollisionController {
    public interface CollisionEvent {
        void onCollision(GameObject a, GameObject b);
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

    private LinkedList<CollisionPair> forceBruteCheck = new LinkedList<CollisionPair>();

    public boolean detectaColisao(GameObject a, GameObject b) {
        return boxCollider.detectaColisao(a, b);
    }

    public void watchForCollision(@NonNull GameObject a, @NonNull GameObject b, @NonNull CollisionEvent event) {
        forceBruteCheck.add(new CollisionPair(a, b, event));
    }

    public void doBruteForceCheck() {

        Iterator<CollisionPair> iterator = forceBruteCheck.iterator();

        while (iterator.hasNext()) {

            CollisionPair pair = iterator.next();

            Rectangle aBounds = pair.a.getBounds();
            Rectangle bBounds = pair.b.getBounds();

            if (aBounds.intersects(bBounds)) {
                pair.event.onCollision(pair.a, pair.b);
            }
        }
    }
}
