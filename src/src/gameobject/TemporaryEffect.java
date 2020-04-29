package src.gameobject;

import src.base.Sprite;
import src.base.Vector2d;

public class TemporaryEffect extends GameObject {
    double lifeTimeSeconds;

    public TemporaryEffect(Vector2d pos, double lifeTimeSeconds) {
        super(false);
        this.pos.copy(pos);
        this.lifeTimeSeconds = lifeTimeSeconds;
    }


    @Override
    public void start() {
        renderer.setSprite(new Sprite("explosion3.png", 20));

        renderer.getPos().copy(pos);
    }


    double totalTime = 0;
    @Override
    public void update(double dTime) {
        totalTime += dTime;

        if (totalTime > lifeTimeSeconds) {
            destroy();
        }
    }

    @Override
    public void draw() {
        renderer.draw();
    }
}
