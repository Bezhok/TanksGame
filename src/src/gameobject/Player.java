package src.gameobject;

import src.Main;
import src.base.InputEvent;
import src.base.Movement;
import src.base.Sprite;
import src.base.Vector2d;
import src.observer.Observer;

public class Player extends GameObject implements Observer {
    public Gun gun = new Gun();
    private Movement movement;

    public Player() {
        movement = new Movement(this);
    }

    private void updateComponentsPos() {
        gun.getPos().update(pos.x, pos.y - 10);
        renderer.getPos().copy(pos);
        collider.getPos().copy(pos);
    }

    @Override
    public void start() {
        renderer.setSprite(new Sprite("tank2.png", 50));
        size.x = renderer.getSprite().getSize().x;
        size.y = renderer.getSprite().getSize().y;

        updateComponentsPos();
        collider.getSize().copy(size);
    }

    @Override
    public void update(double dTime) {
        var prev = new Vector2d(pos.x, pos.y);
        var prevVel = new Vector2d(movement.getVelocity().x, movement.getVelocity().y);

        movement.moveX(dTime);
        if (collider.collided()) {
            pos = prev;
            movement.setVelocity(prevVel);
            pos.x -= 1;

            movement.getVelocity().x = 0;
        }

        if (pos.x <= 0) pos.x = 1;
        if (pos.x >= 800) pos.x = 800;

        updateComponentsPos();
    }

    @Override
    public void draw() {
        renderer.draw();
        gun.draw();
    }

    @Override
    public void onNotify(InputEvent event) {
        var keyEvent = event.keyEvent;
        switch (keyEvent.getCode()) {
            case W:
                if (event.isStart) {
                    gun.updateAngle(-0.05);
                }
                break;
            case S:
                if (event.isStart) {
                    gun.updateAngle(0.05);
                }
                break;
            case A:
                if (event.isStart) {
                    movement.getAcceleration().x = -500;
                } else {
                    movement.getAcceleration().x = 0;
                }
                break;
            case D:
                if (event.isStart) {
                    movement.getAcceleration().x = 500;
                } else {
                    movement.getAcceleration().x = 0;
                }
                break;
            case C:
                if (event.isEnd)
                    makeShot();
                break;
        }
    }

    private void makeShot() {
        Bullet bullet = new Bullet();
Main.gameObjects.add(bullet);

        var bulletStartPoint = new Vector2d(gun.getPos().x, gun.getPos().y);
        bulletStartPoint.x += gun.getDir().x*gun.getGunLen();
        bulletStartPoint.y += gun.getDir().y*gun.getGunLen();

        bullet.setPos(bulletStartPoint);
        bullet.setStartVelocity(gun.getDir());

        bullet.getRenderer().setGc(Main.gc);

        bullet.start();
    }
}
