package src.base;

import javafx.geometry.Point2D;
import src.gameobject.GameObject;

public class Movement {
    Vector2d velocity = new Vector2d(0, 0);
    Vector2d acceleration = new Vector2d(0, 98);
    Vector2d maxSpeed = new Vector2d(100, 100);

    GameObject gameObject;

    public Movement(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void moveY(double dTime) {
        if (dTime > 1) dTime = 1;
        velocity.y += dTime*acceleration.y;
        if (velocity.y > maxSpeed.y) velocity.y = maxSpeed.y;

        gameObject.getPos().y += dTime*velocity.y;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public Vector2d getAcceleration() {
        return acceleration;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void moveX(double dTime) {
        if (dTime > 0.02) dTime = 0.02;
        velocity.x += dTime*acceleration.x-velocity.x*0.1;
        if (velocity.x > maxSpeed.x) velocity.x = maxSpeed.x;
        if (velocity.x < -maxSpeed.x) velocity.x = -maxSpeed.x;

        gameObject.getPos().x += dTime*velocity.x;
    }
}
