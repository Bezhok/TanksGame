package src.gameobject;

import javafx.geometry.Point2D;

public abstract class GameObject {
    Point2D position;
    Point2D size;

    abstract void update(double dTime);
    abstract void draw();
}
