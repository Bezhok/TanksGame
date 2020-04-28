package src.gameobject;

import src.base.Sprite;

public class Player extends GameObject {
    @Override
    public void start() {
        renderer.setSprite(new Sprite("tank1.jpg", 100));
        size.x = renderer.getSprite().getSize().x;
        size.y = renderer.getSprite().getSize().y;

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
