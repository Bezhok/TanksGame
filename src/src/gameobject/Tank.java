package src.gameobject;

import src.Main;
import src.base.InputEvent;
import src.base.Movement;
import src.base.Sprite;
import src.base.Vector2d;
import src.observer.Observer;

public class Tank extends GameObject {
    public Gun gun = new Gun();
    protected Movement movement;

    public Tank() {
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

    protected void updateMovement(double dTime) {
        var prev = new Vector2d(pos.x, pos.y);
        var prevVel = new Vector2d(movement.getVelocity().x, movement.getVelocity().y);

        double deltaX = movement.moveX(dTime);
        if (collider.collided()) {
            pos = prev;
            movement.setVelocity(prevVel);

            if (deltaX > 0) {
                pos.x -= 1;
            }
            else if (deltaX < 0) {
                pos.x += 1;
            }

            movement.getVelocity().x = 0;
        }

        if (pos.x <= 0) pos.x = 1;
        if (pos.x >= 800) pos.x = 800;

        updateComponentsPos();
    }

    @Override
    public void update(double dTime) {
        updateMovement(dTime);
    }

    @Override
    public void draw() {
        renderer.draw();
        gun.draw();
    }

    protected void makeShot() {
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
