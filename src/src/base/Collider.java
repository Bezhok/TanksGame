package src.base;

import javafx.geometry.Rectangle2D;
import src.gameobject.GameObject;

import java.util.ArrayList;

public class Collider {
    Vector2d pos = new Vector2d();
    Vector2d size = new Vector2d();
    GameObject gameObject;

    public Collider(GameObject gameObject) {
        this.gameObject = gameObject;

        colliders.add(this);
    }

    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    public Vector2d getSize() {
        return size;
    }

    public void setSize(Vector2d size) {
        this.size = size;
    }

    public static ArrayList<Collider> getColliders() {
        return colliders;
    }

    static ArrayList<Collider> colliders = new ArrayList<>();

    public GameObject getGameObject() {
        return gameObject;
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(pos.x-size.x/2, pos.y-size.y/2, size.x, size.y);
    }

    public boolean intersects(Collider another)
    {
        return another.getBoundary().intersects(getBoundary());
    }

    public ArrayList<GameObject> collided() {
        var collidedWith = new ArrayList<GameObject>();
        for (var collider: colliders) {
            if (collider != this) {
                if (this.intersects(collider)) {
//                    collider.gameObject.onCollision(this);
//                    gameObject.onCollision(collider);

                    collidedWith.add(collider.gameObject);
                }
            }
        }

        Collider.getColliders().removeIf(collider -> collider.getGameObject().wasDestroyed());

        return collidedWith;
    }

    public static void processCollision() {
        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i+1; j < colliders.size(); j++) {
                if (colliders.get(i).intersects(colliders.get(j))) {
                    colliders.get(i).gameObject.onCollision(colliders.get(j));
                    colliders.get(j).gameObject.onCollision(colliders.get(i));
                }
            }
        }

        Collider.getColliders().removeIf(collider -> collider.getGameObject().wasDestroyed());
    }
}
