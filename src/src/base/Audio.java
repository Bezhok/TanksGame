package src.base;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Audio {
    String filePath;
    double volume = 1;

    public Audio(String fileName, double volume) {
        this(fileName);
        this.volume = volume;
    }

    public Audio(String fileName) {
        filePath = new File("resources/" + fileName).toURI().toString();
    }

    public void play() {
        AudioClip clip = new AudioClip(filePath);
        clip.setVolume(volume);
        clip.play();
    }
}
