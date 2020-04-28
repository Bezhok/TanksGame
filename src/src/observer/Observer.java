package src.observer;

import src.base.InputEvent;

public interface Observer {
    void onNotify(InputEvent event);
}
