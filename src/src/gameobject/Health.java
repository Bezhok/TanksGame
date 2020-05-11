package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Health extends GameObject {
    int max;
    int curr;

    double healthViewLen;

    public Health(int maxHealth, double healthViewLen) {
        this.healthViewLen = healthViewLen;
        this.max = maxHealth;
        this.curr = max;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);

        if (curr > 0)
            gc.strokeLine(pos.x, pos.y, pos.x + curr / (double) max * healthViewLen, pos.y);
    }
}
