package src.gameobject;

import javafx.scene.paint.Color;
import src.Main;
import src.base.Vector2d;

public class Health extends GameObject {
    int max;
    int curr;

    double healthViewLen;

    public Health(int maxHealth, double healthViewLen) {
        this.healthViewLen = healthViewLen;
        this.max = maxHealth;
        this.curr = max;
    }

    public void start() {

    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw() {
        var gc = Main.gc;
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);

        if (curr > 0)
        gc.strokeLine(pos.x, pos.y, pos.x + curr/(double)max * healthViewLen, pos.y);
    }
}
