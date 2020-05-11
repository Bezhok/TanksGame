package src.observer;

import src.base.InputEvent;

public interface Subject {
    void add(Observer observer);

    void remove(Observer observer);

    void notifyAll(InputEvent event);
}
