package src.base;

import src.gameobject.Bullet;

public interface BulletGenerator {
    void onBulletDestroyed(Bullet bullet);
}
