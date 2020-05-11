package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import src.Main;
import src.base.*;
import src.base.math.Vector2d;

import java.util.ArrayList;

public class Bullet extends GameObject {
    private final Movement movement;
    private final Audio explosionAudio;
    private BulletGenerator creator;

    public Bullet(String spriteName) {
        movement = new Movement(this);
        movement.setResistance(0.002);
        movement.getMaxSpeed().x = 10000;
        movement.getMaxSpeed().y = 10000;
        movement.getAcceleration().y = 198;

        renderer.setSprite(new Sprite(spriteName, 10));
        explosionAudio = new Audio("expl.mp3", 0.02);
    }

    public void setCreator(BulletGenerator creator) {
        this.creator = creator;
    }

    public void setStartVelocity(Vector2d vector2d, double power) {

        movement.setVelocity(new Vector2d(vector2d.x * power, vector2d.y * power));
    }

    @Override
    public void start() {
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
        var collidedWith = collider.collidedWith();
        if (!collidedWith.isEmpty()) {
            movement.getVelocity().y = 0;

            destroyCollidedWithBullets(collidedWith);
            return;
        }

        movement.moveX(dTime);
        collidedWith = collider.collidedWith();
        if (!collidedWith.isEmpty()) {
            movement.getVelocity().x = 0;

            destroyCollidedWithBullets(collidedWith);
            return;
        }

        if (pos.x <= 0) destroy();
        if (pos.x >= Main.width) destroy();

        updateComponentsPos();
    }

    private void destroyCollidedWithBullets(ArrayList<GameObject> collidedWith) {
        for (var obj : collidedWith) {
            if (obj instanceof Bullet) {
                obj.destroy();
                destroy();
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        renderer.draw(gc);
    }

    @Override
    public void onCollision(Collider another) {
        if (wasDestroyed) return;
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();

        TemporaryEffect temporaryEffect = new TemporaryEffect("explosion4.png", pos, 0.2, 30);
        temporaryEffect.start();
        Main.gameObjectsBuffer.add(temporaryEffect);

        if (creator != null) {
            creator.onBulletDestroyed(this);
        }

        explosionAudio.play();
    }
}
