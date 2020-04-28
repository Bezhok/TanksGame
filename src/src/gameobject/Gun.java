package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.base.Vector2d;

public class Gun extends GameObject {
    Color color = Color.BLACK;
    int lineWidth = 5;
    double gunLen = 30;

    Vector2d dir = new Vector2d(Math.cos(0), Math.sin(0));
    double angle = 0;


    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    GraphicsContext gc;

    public void updateAngle(double dAngle) {
        angle += dAngle;


        if (angle < -Math.PI) { angle = -Math.PI;}
        if (angle > 0) { angle = 0;}


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
        gc.strokeLine(pos.x, pos.y, pos.x+dir.x*gunLen, pos.y+dir.y*gunLen);
    }
}
