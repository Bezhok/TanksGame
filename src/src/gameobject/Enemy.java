package src.gameobject;

import src.base.AI;
import src.base.Vector2d;

public class Enemy extends Tank {
    private AI ai;
    Tank target;

    public Enemy(Tank target) {
        super();
        this.target = target;
        ai = new AI(this);
        ai.setTarget(target);
//        gun.setAngle(-2);
        gun.updateAngle(-2);
    }

    @Override
    public void onBulletDestroyed(Bullet bullet) {
        ai.onBulletDestroyed(bullet);
    }

    @Override
    public void update(double dTime) {
        super.update(dTime);

        ai.update(dTime);
    }
}
