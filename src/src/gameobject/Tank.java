package src.gameobject;

import src.Main;
import src.base.Collider;
import src.base.Movement;
import src.base.Sprite;
import src.base.Vector2d;

public class Tank extends GameObject {
    public Gun gun = new Gun();

    public Movement getMovement() {
        return movement;
    }

    protected Movement movement;
    protected Health health;


    public Tank() {
        movement = new Movement(this);
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
        if (collider.collided()) {
            movement.setVelocity(prevVel);
            if (deltaX > 0) {
                pos.x -= deltaX + 2;
            } else if (deltaX < 0) {
                pos.x += deltaX + 2;
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

    double power = 350;

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

        bullet.start();
    }

    @Override
    public void onCollision(Collider other) {
        if (other.getGameObject() instanceof Bullet) {
            System.out.println(health.curr);
            health.curr -= 10;

        }

    }

    public void destroy() {
        super.destroy();

        TemporaryEffect temporaryEffect = new TemporaryEffect(pos, 0.2, 100);
        temporaryEffect.getRenderer().setGc(Main.gc);
        temporaryEffect.start();
        Main.temporyGameObjects.add(temporaryEffect);
    }
}
