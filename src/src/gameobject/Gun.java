package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.base.Vector2d;

public class Gun extends GameObject {
    Color color = Color.BLACK;
    int lineWidth = 5;
    double gunLen = 30;


    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    GraphicsContext gc;


    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw() {
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
        gc.strokeLine(pos.x, pos.y, pos.x+Math.cos(-0)*gunLen, pos.y+Math.sin(-0)*gunLen);
    }
}
