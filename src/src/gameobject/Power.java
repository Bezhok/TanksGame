package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Power extends GameObject {
    int min, max;
    int curr;
    double powerViewLen;

    public Power(int minPower, int maxPower, int currPower, double powerViewLen) {
        this.powerViewLen = powerViewLen;
        this.max = maxPower;
        this.curr = currPower;
        this.min = minPower;
    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        if (curr > min)
            gc.strokeLine(pos.x, pos.y, pos.x + (curr - min) / (double) (max - min) * powerViewLen, pos.y);
    }
}
