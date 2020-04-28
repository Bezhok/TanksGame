package src.gameobject;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import src.base.InputEvent;
import src.base.Movement;
import src.base.Sprite;
import src.base.Vector2d;
import src.observer.Observer;

public class Player extends GameObject implements Observer {
    Movement movement;

    public Player() {
        movement = new Movement(this);
    }

    @Override
    public void start() {
        renderer.setSprite(new Sprite("tank2.png", 50));
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

//        System.out.println(movement.getVelocity().x);
        movement.moveX(dTime);
        if (collider.collided()) {
            pos = prev;
            movement.setVelocity(prevVel);
            pos.x -= 1;

            movement.getVelocity().x = 0;
        }

        if (pos.x <= 0) pos.x = 2;
        if (pos.x >= 800) pos.x = 800;

        renderer.getPos().x = pos.x;
        renderer.getPos().y = pos.y;
        collider.getPos().x = pos.x;
        collider.getPos().y = pos.y;
    }

    @Override
    public void draw() {
        renderer.draw();
    }

    @Override
    public void onNotify(InputEvent event) {
        var keyEvent = event.keyEvent;
        switch (keyEvent.getCode()) {
            case W:
                break;
            case A:
                if (event.isStart) {
                    movement.getAcceleration().x = -500;
                } else {
                    movement.getAcceleration().x = 0;
//                    movement.getVelocity().x = 0;
                }
                break;
            case S:
                break;
            case D:
                if (event.isStart) {
                    movement.getAcceleration().x = 500;
                } else {
                    movement.getAcceleration().x = 0;
//                    movement.getVelocity().x = 0;
                }
                break;

        }
    }
}
