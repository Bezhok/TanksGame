package src.base;

import javafx.geometry.Point2D;
import src.gameobject.GameObject;

public class Movement {
    Vector2d velocity = new Vector2d(0, 0);
    Vector2d acceleration = new Vector2d(0, 98);

    public Vector2d getMaxSpeed() {
        return maxSpeed;
    }

    Vector2d maxSpeed = new Vector2d(100, 100);
    double resistance = 0.1;
    GameObject gameObject;

    public void setResistance(double resistance) {
        if (resistance > 1) this.resistance = 1;
        else if (resistance < 0) this.resistance = 0;
        else this.resistance = resistance;
    }
    public Movement(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public double moveY(double dTime) {
        if (dTime > 0.02) dTime = 0.02;


        velocity.y += dTime*acceleration.y;

        // check vector len not this stupid thing
        if (velocity.y > maxSpeed.y) velocity.y = maxSpeed.y;
        if (velocity.y < -maxSpeed.y) velocity.y = -maxSpeed.y;

        double deltaY = dTime*velocity.y;
        gameObject.getPos().y += deltaY;

        return deltaY;
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

    public double moveX(double dTime) {
        if (dTime > 0.02) dTime = 0.02;


        velocity.x += dTime*acceleration.x-velocity.x*resistance;

        // check vector len not this stupid thing
        if (velocity.x > maxSpeed.x) velocity.x = maxSpeed.x;
        if (velocity.x < -maxSpeed.x) velocity.x = -maxSpeed.x;

        double deltaX = dTime*velocity.x;
        gameObject.getPos().x += deltaX;

        return deltaX;
    }
}
