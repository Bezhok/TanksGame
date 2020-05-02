package src.base;

import src.gameobject.Tank;

public class AI {
    Tank tank;

    public AI(Tank tank) {
        this.tank = tank;
    }

    double timer = 0;
    public void update(double dTime, final Tank target) {
        timer += dTime;



        double bulletPos = tank.getPos().x - tank.getPower()*tank.getPower()*2*tank.gun.getDir().x*tank.gun.getDir().y/198+50;
        double delta = target.getPos().x - bulletPos;

        System.out.println(bulletPos + " " + target.getPos().x + " " + delta);
        if (Math.abs(delta) > 5) {
            tank.getMovement().getAcceleration().x = Math.signum(delta)*500;
            tank.getMovement().moveX(dTime);
        } else {
            tank.getMovement().getAcceleration().x = 0;
        }

        if (timer > 1.0) {

            tank.makeShot();
            timer = 0;
        }
    }
}
