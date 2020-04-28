package src.base;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Collider {
    Point2D upperLeft = new Point2D(0, 0),
            downRight = new Point2D(0, 0),
            center = new Point2D(0, 0),
            size = new Point2D(0, 0);


    static ArrayList<Collider> colliders = new ArrayList<>();
    Collider() {
        colliders.add(this);
    }
}
