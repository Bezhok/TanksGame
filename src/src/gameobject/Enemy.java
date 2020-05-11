package src.gameobject;

import src.base.AI;

public class Enemy extends Tank {
    private final AI ai;
    private final Tank target;

    public Enemy(String spiteName, Tank target) {
        super(spiteName);
        this.target = target;
        ai = new AI(this);
        ai.setTarget(target);
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
