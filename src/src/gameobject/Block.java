package src.gameobject;

import javafx.scene.shape.Rectangle;
import src.base.Sprite;
import src.base.Vector2d;

public class Block extends GameObject {
    @Override
    public void start() {
        renderer.setSprite(new Sprite("wall.jpg", (int)size.x, (int)size.y));

        renderer.getPos().x = pos.x;
        renderer.getPos().y = pos.y;
        collider.getPos().x = pos.x;
        collider.getPos().y = pos.y;
        collider.getSize().x = size.x;
        collider.getSize().y = size.y;
    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw() {
        renderer.draw();
    }
}
