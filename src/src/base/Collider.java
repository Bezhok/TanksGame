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
        boolean wasCollision = false;
        var collidedWith = new ArrayList<GameObject>();
        for (var collider: colliders) {
            if (collider != this) {
                if (this.intersects(collider)) {
                    collider.gameObject.onCollision(this);
                    gameObject.onCollision(collider);

                    collidedWith.add(collider.gameObject);
                    wasCollision = true;
                }
            }
        }

        Collider.getColliders().removeIf(collider -> collider.getGameObject().wasDestroyed());
//        return wasCollision;
        return collidedWith;
    }
}
