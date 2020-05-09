package src.base;

import src.Main;
import src.gameobject.Bullet;
import src.gameobject.Tank;

import java.util.Random;

public class AI {
    Tank tank;

    public void setTarget(Tank target) {
        this.target = target;
    }

    public AI(Tank tank) {
        this.tank = tank;
    }

    double timer = 0;
    double prevShotDelta = 0;

    Vector2d prevBulletPos;
    boolean miss = false;
    public void onBulletDestroyed(Bullet bullet) {
       Vector2d currPos = new Vector2d();
       currPos.copy(bullet.getPos());

       if (prevBulletPos != null) {
           if (Math.abs(currPos.y - prevBulletPos.y) < 5 && Math.abs(currPos.y - target.getPos().y) > 15) {
               miss = true;
           } else {
               miss = false;
           }
       }
       if (currPos.x <= 0 || currPos.x >= Main.width) {
           tank.setCurrPower(tank.getCurrPower() - 10);
       }

       prevBulletPos = currPos;
    }

    Random randomGenerator = new Random();

    int randomInt = randomGenerator.nextInt(100);

    Tank target;

    public void update(double dTime) {
        timer += dTime;



        double bulletPos = tank.getPos().x - tank.getCurrPower()*tank.getCurrPower()*2*tank.gun.getDir().x*tank.gun.getDir().y/198+50;
        double delta = target.getPos().x - bulletPos;

        if (Math.abs(delta) > 5) {
            tank.getMovement().getAcceleration().x = Math.signum(delta)*500;
            tank.getMovement().moveX(dTime);
        } else {
            tank.getMovement().getAcceleration().x = 0;
        }


        if (timer > randomInt/100.0) {
            if (Math.abs(prevShotDelta - delta) < 2 && Math.abs(delta) > 5) {
                tank.gun.updateAngle(Math.signum(delta)*0.1);
            } else if (miss) {
//                tank.setCurrPower(tank.setCurrPower() + 20);
            }

            tank.makeShot();
            timer = 0;

            randomInt = randomGenerator.nextInt(100);
            prevShotDelta = delta;
        }
    }
}
