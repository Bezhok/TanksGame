package src.base;

import javafx.scene.Scene;
import src.observer.Observer;
import src.observer.Subject;

import java.util.ArrayList;

public class InputHandler implements Subject {
    private final ArrayList<Observer> observers = new ArrayList<>();
    private final Scene scene;

    public InputHandler(Scene scene) {
        this.scene = scene;

        scene.setOnKeyPressed(e -> {
            var inputEvent = new InputEvent();
            inputEvent.isStart = true;
            inputEvent.keyEvent = e;
            notifyAll(inputEvent);
        });

        scene.setOnKeyReleased(e -> {
            var inputEvent = new InputEvent();
            inputEvent.isEnd = true;
            inputEvent.keyEvent = e;
            notifyAll(inputEvent);
        });
    }

    public void clear() {
        observers.clear();
    }

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.removeIf(observer::equals);
    }

    @Override
    public void notifyAll(InputEvent event) {
        for (var observer : observers) {
            observer.onNotify(event);
        }
    }
}
