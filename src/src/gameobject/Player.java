package src.gameobject;

import src.base.Movement;
import src.base.Sprite;
import src.base.Vector2d;

public class Player extends GameObject {
    Movement movement;

    public Player() {
        movement = new Movement(this);
    }

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
        var prev = new Vector2d(pos.x, pos.y);
        var prevVel = new Vector2d( movement.getVelocity().x,  movement.getVelocity().y);
        
        movement.moveX(dTime);
        if (collider.collided()) {
            pos = prev;
            movement.setVelocity(prevVel);
            //pos.x -= 1;
        }

        if (pos.x <= 0) pos.x = 0;
        if (pos.x >= 800) pos.x = 200;

        renderer.getPos().x = pos.x;
        renderer.getPos().y = pos.y;
        collider.getPos().x = pos.x;
        collider.getPos().y = pos.y;
    }

    @Override
    public void draw() {
        renderer.draw();
    }
}
