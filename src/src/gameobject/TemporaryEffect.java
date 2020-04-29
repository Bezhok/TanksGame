package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import src.base.Sprite;
import src.base.Vector2d;

import java.util.Random;

public class TemporaryEffect extends GameObject {
    double lifeTimeSeconds;

    public TemporaryEffect(Vector2d pos, double lifeTimeSeconds) {
        super(false);
        this.pos.copy(pos);
        this.lifeTimeSeconds = lifeTimeSeconds;
    }

    int randomInt;
    @Override
    public void start() {
        renderer.setSprite(new Sprite("explosion3.png", 30));

        renderer.getPos().copy(pos);

        Random randomGenerator = new Random();

        randomInt = randomGenerator.nextInt(360) - 180;
    }


    double totalTime = 0;
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
    public void draw() {
        renderer.getGc().save();


        rotate(renderer.getGc(), randomInt, pos.x, pos.y);

        renderer.draw();
        renderer.getGc().restore();
    }
}
