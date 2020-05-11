package src.base.math;

public class Vector2d {
    public double x, y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d() {
    }

    public void update(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void copy(Vector2d vector2d) {
        this.x = vector2d.x;
        this.y = vector2d.y;
    }
}
