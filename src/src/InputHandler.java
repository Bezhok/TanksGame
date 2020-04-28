package src;

import javafx.event.Event;
import javafx.scene.Scene;
import src.observer.Observer;
import src.observer.Subject;

import java.util.ArrayList;

public class InputHandler implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();
    private Scene scene;

    public InputHandler(Scene scene) {
        this.scene = scene;
        scene.setOnKeyReleased(this::notifyAll);
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
    public void notifyAll(Event event) {
        for (var observer : observers) {
            observer.onNotify(event);
        }
    }
}
