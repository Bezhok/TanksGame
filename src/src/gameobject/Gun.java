package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.base.Vector2d;

public class Gun extends GameObject {
    private Color color = Color.BLACK;
    private int lineWidth = 5;
    private double gunLen = 30;

    public double getGunLen() {
        return gunLen;
    }

    public Vector2d getDir() {
        return dir;
    }

    private Vector2d dir = new Vector2d(Math.cos(0), Math.sin(0));
    private double angle = 0;
    private GraphicsContext gc;

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setGunLen(double gunLen) {
        this.gunLen = gunLen;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void updateAngle(double dAngle) {
        angle += dAngle;

        if (angle < -Math.PI) {
            angle = -Math.PI;
        }
        if (angle > 0) {
            angle = 0;
        }

        dir.x = Math.cos(angle);
        dir.y = Math.sin(angle);
    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw() {
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
        gc.strokeLine(pos.x, pos.y, pos.x + dir.x * gunLen, pos.y + dir.y * gunLen);
    }
}
