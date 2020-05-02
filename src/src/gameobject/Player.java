package src.gameobject;

import src.base.InputEvent;
import src.observer.Observer;

public class Player extends Tank implements Observer {

    @Override
    public void onNotify(InputEvent event) {
        var keyEvent = event.keyEvent;
        switch (keyEvent.getCode()) {
            case W:
                if (event.isStart) {
                    gun.updateAngle(-0.05);
                }
                break;
            case S:
                if (event.isStart) {
                    gun.updateAngle(0.05);
                }
                break;
            case A:
                if (event.isStart) {
                    movement.getAcceleration().x = -500;
                } else {
                    movement.getAcceleration().x = 0;
                }
                break;
            case D:
                if (event.isStart) {
                    movement.getAcceleration().x = 500;
                } else {
                    movement.getAcceleration().x = 0;
                }
                break;
            case C:
                if (event.isEnd)
                    makeShot();
                break;
            case Z:
                if (event.isStart)
                    setPower(power - 5);
                break;
            case X:
                if (event.isStart)
                    setPower(power + 5);
                break;
        }
    }
}
