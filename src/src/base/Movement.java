package src.base;

import javafx.geometry.Point2D;
import src.gameobject.GameObject;

public class Movement {
    Vector2d velocity = new Vector2d(0, 0);
    double acceleration = 98;

    GameObject gameObject;

    public Movement(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void moveY(double dTime) {
        if (dTime > 1) dTime = 1;
        velocity.y += dTime*acceleration;
        if (velocity.y > 100) velocity.y = 100;

        gameObject.getPos().y += dTime*velocity.y;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void moveX(double dTime) {
        if (dTime > 1) dTime = 1;
        velocity.x += dTime*100;
        if (velocity.x > 100) velocity.x = 100;

        gameObject.getPos().x += dTime*velocity.x;
    }
}
