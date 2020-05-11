package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import src.base.Sprite;

public class Block extends GameObject {
    @Override
    public void start() {
        renderer.setSprite(new Sprite("ground.png", (int) size.x, (int) size.y));

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
    public void draw(GraphicsContext gc) {
        renderer.draw(gc);
    }
}
