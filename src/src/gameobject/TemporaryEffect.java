package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import src.base.Sprite;
import src.base.math.Vector2d;

import java.util.Random;

public class TemporaryEffect extends GameObject {
    private final double lifeTimeSeconds;
    private int scale = 30;
    private int randomInt;
    private double totalTime = 0;

    public TemporaryEffect(String spriteName, Vector2d pos, double lifeTimeSeconds, int scale) {
        super(false);
        this.pos.copy(pos);
        this.lifeTimeSeconds = lifeTimeSeconds;
        this.scale = scale;

        renderer.setSprite(new Sprite(spriteName, scale));
    }

    @Override
    public void start() {
        renderer.getPos().copy(pos);

        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(360) - 180;
    }

    @Override
    public void update(double dTime) {
        totalTime += dTime;

        if (totalTime > lifeTimeSeconds) {
            destroy();
        }
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        rotate(gc, randomInt, pos.x, pos.y);
        renderer.draw(gc);

        gc.restore();
    }
}
