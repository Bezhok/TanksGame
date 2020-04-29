package src.gameobject;

import src.Main;
import src.base.Collider;
import src.base.Movement;
import src.base.Sprite;
import src.base.Vector2d;

public class Bullet extends GameObject {
    private Movement movement;

    public void setStartVelocity(Vector2d vector2d) {
        double power = 350;
        movement.setVelocity(new Vector2d(vector2d.x*power, vector2d.y*power));
    }

    public Bullet() {
        movement = new Movement(this);
        movement.setResistance(0.002);

        movement.getMaxSpeed().x = 10000;
        movement.getMaxSpeed().y = 10000;
        movement.getAcceleration().y = 198;
    }

    @Override
    public void start() {
        renderer.setSprite(new Sprite("wall.jpg", 5, 5));

        updateComponentsPos();
        collider.getSize().copy(new Vector2d(5, 5));
    }

    private void updateComponentsPos() {
        renderer.getPos().copy(pos);
        collider.getPos().copy(pos);
    }

    @Override
    public void update(double dTime) {
        movement.moveY(dTime);
        if (collider.collided()) {
            movement.getVelocity().y = 0;
        }

        movement.moveX(dTime);
        if (collider.collided()) {
            movement.getVelocity().x = 0;
        }

        if (pos.x <= 0) pos.x = 1;
        if (pos.x >= 800) pos.x = 800;

        updateComponentsPos();
    }

    @Override
    public void draw() {
        renderer.draw();
    }

    @Override
    public void onCollision(Collider another) {
        if (wasDestroyed) return;

        if (collider.getGameObject() instanceof Enemy) {
            // destroy enemy
        }

        destroy();
    }

    @Override
    void destroy() {
        super.destroy();

        TemporaryEffect temporaryEffect = new TemporaryEffect(pos, 0.2);
        temporaryEffect.getRenderer().setGc(Main.gc);
        temporaryEffect.start();
        Main.temporyGameObjects.add(temporaryEffect);
    }
}
