package src.gameobject;

import src.Main;
import src.base.*;

public class Tank extends GameObject implements BulletGenerator {
    public Gun gun = new Gun();
    protected Movement movement;
    protected Health health;
    double power = 350;


    public Tank() {
        movement = new Movement(this);
    }

    public Movement getMovement() {
        return movement;
    }

    private void updateComponentsPos() {
        gun.getPos().update(pos.x, pos.y - 10);
        renderer.getPos().copy(pos);
        collider.getPos().copy(pos);

        health.getPos().x = pos.x - renderer.getSprite().getSize().x / 2.0;
        health.getPos().y = pos.y - renderer.getSprite().getSize().y / 2.0 - 10;
    }

    @Override
    public void start() {
        renderer.setSprite(new Sprite("tank2.png", 50));
        size.x = renderer.getSprite().getSize().x;
        size.y = renderer.getSprite().getSize().y;

        health = new Health(100, size.x);
        updateComponentsPos();
        collider.getSize().copy(size);
    }

    protected void updateMovement(double dTime) {
        var prevVel = new Vector2d(movement.getVelocity().x, movement.getVelocity().y);

        double deltaX = movement.moveX(dTime);

        var collidedWith = collider.collided();
        boolean isCollided = false;

        for (var c : collidedWith) {
            if (!(c instanceof Bullet)) {
                isCollided = true;
                Collider aCollider = c.collider;

                if (collider.getPos().x < aCollider.getPos().x) {
                    pos.x = aCollider.
                            getPos().x - aCollider.getSize().x / 2 - collider.getSize().x / 2;
                } else if (collider.getPos().x > aCollider.getPos().x) {
                    pos.x = aCollider.getPos().x + aCollider.getSize().x / 2 + collider.getSize().x / 2;
                }
            }
        }

        if (isCollided) {
            movement.setVelocity(prevVel);
            movement.getVelocity().x = 0;
        }

        if (pos.x <= 0) pos.x = 1;
        if (pos.x >= 800) pos.x = 800;

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
    public void draw() {
        renderer.draw();
        gun.draw();
        health.draw();
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void makeShot() {
        Bullet bullet = new Bullet();
        Main.gameObjectsTemp.add(bullet);

        var bulletStartPoint = new Vector2d(gun.getPos().x, gun.getPos().y);
        bulletStartPoint.x += gun.getDir().x * gun.getGunLen();
        bulletStartPoint.y += gun.getDir().y * gun.getGunLen();

        bullet.setPos(bulletStartPoint);
        bullet.setStartVelocity(gun.getDir(), power);

        bullet.getRenderer().setGc(Main.gc);

        bullet.setCreator(this);
        bullet.start();
    }

    @Override
    public void onCollision(Collider other) {
        if (other.getGameObject() instanceof Bullet) {
            System.out.println(health.curr);
            health.curr -= 10;

        }

    }

    @Override
    public void onBulletDestroyed(Bullet bullet) {
    }

    public void destroy() {
        super.destroy();

        TemporaryEffect temporaryEffect = new TemporaryEffect(pos, 0.2, 100);
        temporaryEffect.getRenderer().setGc(Main.gc);
        temporaryEffect.start();
        Main.temporyGameObjects.add(temporaryEffect);
    }
}
