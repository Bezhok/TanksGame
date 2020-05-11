package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import src.Main;
import src.base.BulletGenerator;
import src.base.Collider;
import src.base.Movement;
import src.base.Sprite;
import src.base.math.Vector2d;

public class Tank extends GameObject implements BulletGenerator {
    public Gun gun = new Gun();
    protected Movement movement;
    protected Health health;
    Power power;

    public Tank(String spriteName) {
        movement = new Movement(this);

        renderer.setSprite(new Sprite(spriteName, 50));
        size.x = renderer.getSprite().getSize().x;
        size.y = renderer.getSprite().getSize().y;
    }

    public Movement getMovement() {
        return movement;
    }

    private void updateComponentsPos() {
        gun.getPos().update(pos.x, pos.y - 10);
        renderer.getPos().copy(pos);
        collider.getPos().copy(pos);

        power.getPos().x = health.getPos().x = pos.x - renderer.getSprite().getSize().x / 2.0;
        power.getPos().y = health.getPos().y = pos.y - renderer.getSprite().getSize().y / 2.0 - 10;
        power.getPos().y -= 10;
    }

    @Override
    public void start() {
        health = new Health(100, size.x);
        power = new Power(100, 500, 350, size.x);

        updateComponentsPos();
        collider.getSize().copy(size);
    }

    protected void updateMovement(double dTime) {
        var prevVel = new Vector2d(movement.getVelocity().x, movement.getVelocity().y);

        double deltaX = movement.moveX(dTime);

        var collidedWith = collider.collidedWith();
        boolean isCollided = false;

        for (var c : collidedWith) {
            if (!(c instanceof Bullet)) {
                isCollided = true;
                Collider aCollider = c.collider;

                if (collider.getPos().x < aCollider.getPos().x) {
                    pos.x = aCollider.
                            getPos().x - aCollider.getSize().x / 2 - collider.getSize().x / 2 - 1;
                } else if (collider.getPos().x > aCollider.getPos().x) {
                    pos.x = aCollider.getPos().x + aCollider.getSize().x / 2 + collider.getSize().x / 2 + 1;
                }
            }
        }

        if (isCollided) {
            movement.setVelocity(prevVel);
            movement.getVelocity().x = 0;
        }

        if (pos.x <= 0) pos.x = 1;
        if (pos.x >= Main.width) pos.x = Main.width;

        updateComponentsPos();
    }

    @Override
    public void update(double dTime) {
        updateMovement(dTime);
        if (health.curr <= 0) {
            destroy();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        renderer.draw(gc);
        gun.draw(gc);
        health.draw(gc);
        power.draw(gc);
    }

    public int getCurrPower() {
        return power.curr;
    }

    public void setCurrPower(int currPower) {
        power.curr = Math.min(Math.max(currPower, power.min), power.max);
    }

    public void makeShot() {
        Bullet bullet = new Bullet("bullet3_3.png");
        Main.gameObjectsBuffer.add(bullet);

        var bulletStartPoint = new Vector2d(gun.getPos().x, gun.getPos().y);
        bulletStartPoint.x += gun.getDir().x * gun.getGunLen();
        bulletStartPoint.y += gun.getDir().y * gun.getGunLen();

        bullet.setPos(bulletStartPoint);
        bullet.setStartVelocity(gun.getDir(), power.curr);


        bullet.setCreator(this);
        bullet.start();
    }

    @Override
    public void onCollision(Collider other) {
        if (other.getGameObject() instanceof Bullet) {
            health.curr -= 30;
        }
    }

    @Override
    public void onBulletDestroyed(Bullet bullet) {
    }

    public void destroy() {
        super.destroy();

        TemporaryEffect temporaryEffect = new TemporaryEffect("explosion4.png", pos, 0.2, 100);
        temporaryEffect.start();
        Main.gameObjectsBuffer.add(temporaryEffect);
    }
}
