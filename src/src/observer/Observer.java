package src.observer;

import javafx.event.Event;

public interface Observer {
    void onNotify(Event event);
}
