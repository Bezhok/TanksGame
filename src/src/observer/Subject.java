package src.observer;

import javafx.event.Event;

public interface Subject {
    void add(Observer observer);
    void remove(Observer observer);
    void notifyAll(Event event);
}
