package src.gameobject;

import javafx.scene.shape.Rectangle;
import src.base.Sprite;
import src.base.Vector2d;

public class Block extends GameObject {
    @Override
    public void start() {
        renderer.setSprite(new Sprite("ground.png", (int)size.x, (int)size.y));

        updateComponentsPos();
        collider.getSize().copy(size);
    }

    private void updateComponentsPos() {
        renderer.getPos().copy(pos);
        collider.getPos().copy(pos);
    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw() {
        renderer.draw();
    }
}
