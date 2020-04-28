package src.base;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Collider {
    Vector2d pos = new Vector2d();
    Vector2d size = new Vector2d();


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

    static ArrayList<Collider> colliders = new ArrayList<>();
    public Collider() {
        colliders.add(this);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(pos.x-size.x/2, pos.y-size.y/2, size.x, size.y);
    }

    public boolean intersects(Collider another)
    {
        return another.getBoundary().intersects(getBoundary());
    }
}
