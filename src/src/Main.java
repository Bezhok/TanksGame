package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.base.Collider;
import src.base.InputHandler;
import src.base.Levels;
import src.base.Sprite;
import src.gameobject.Enemy;
import src.gameobject.GameObject;
import src.gameobject.Gun;
import src.gameobject.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Main extends Application {
    public static int width;
    public static int height;
    public static HashSet<GameObject> gameObjectsBuffer = new HashSet<>();
    public static ArrayList<GameObject> staticGameObjects = new ArrayList<>();
    private final HashSet<GameObject> gameObjects = new HashSet<>();
    private Media media;
    private Sprite backgroundSprite;
    private boolean shouldStartNewLevel = false;
    private double timer = 0;
    private boolean isPaused = false;
    private MediaPlayer backgroundPlayer;
    private GraphicsContext gc;
    private InputHandler inputHandler;
    private Group root;
    private Scene theScene;
    private Levels levels;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        reserveMemory();

        stage.setTitle("Game");
        root = new Group();
        theScene = new Scene(root);
        stage.setScene(theScene);
        playBackgroundMusic();
        launch(stage);

        levels = new Levels();
        inputHandler = new InputHandler(theScene);

        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(Math.abs(currentNanoTime - lastUpdate) / 1000000000.0);

                lastUpdate = currentNanoTime;
            }
        }.start();
    }

    private void reserveMemory() {
        // reserve capacity
        for (int i = 0; i < 100; i++) {
            gameObjectsBuffer.add(new Gun());
            gameObjects.add(new Gun());
        }
        gameObjectsBuffer.clear();
        gameObjects.clear();
        Collider.getColliders().ensureCapacity(64);
    }

    private void clearScene() {
        for (var obj : gameObjects) {
            obj.destroy();
        }
        gameObjectsBuffer.clear();
        gameObjects.clear();
        staticGameObjects.clear();

        Collider.getColliders().clear();
    }

    private void playBackgroundMusic() {
        String musicFile = "BeepBox-Song2.wav";
        if (media == null)
            media = new Media(new File("resources/" + musicFile).toURI().toString());

        backgroundPlayer = new MediaPlayer(media);
        backgroundPlayer.setVolume(0.3);

        backgroundPlayer.setStopTime(new Duration(50500));
        backgroundPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                backgroundPlayer.dispose();
                playBackgroundMusic();
            }
        });

        backgroundPlayer.play();
    }

    private void launch(Stage stage) {
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
        width = (int) stage.getWidth();
        height = (int) stage.getHeight();

        canvas.setWidth(width);
        canvas.setHeight(height);

        backgroundSprite = new Sprite("bc.jpg", width);
    }

    private void update(double dTime) {
        gc.drawImage(backgroundSprite.getImage(), 0, 0);

        if (!isPaused) {
            updateObjects(dTime);
        }

        if (isPaused) {
            timer += dTime;
            printCurrLevel();
        } else if (shouldStartNewLevel) {
            isPaused = true;
            clearScene();
            levels.loadNext(inputHandler);
        }

        if (timer >= 2) {
            isPaused = false;
        }

        if (!isPaused) {
            isPaused = false;
            shouldStartNewLevel = false;
            timer = 0;

            draw();
            postProcessing();
        }
    }

    private void updateObjects(double dTime) {
        boolean isEnemyExists = false;
        boolean isPlayerExists = false;
        for (var obj : gameObjects) {
            if (obj instanceof Enemy) isEnemyExists = true;
            if (obj instanceof Player) isPlayerExists = true;
            obj.update(dTime);
        }

        if (!isPlayerExists) {
            levels.reset();
            shouldStartNewLevel = true;
        }
        if (!isEnemyExists) {
            shouldStartNewLevel = true;
        }
    }

    private void printCurrLevel() {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 60));

        int levelIdx = levels.getCurrLevel() + 1;
        gc.fillText(
                "level " + levelIdx,
                width / 2,
                height / 2
        );
    }

    private void postProcessing() {
        gameObjects.addAll(gameObjectsBuffer);
        gameObjectsBuffer.clear();

        Collider.processCollisions();
        gameObjects.removeIf(GameObject::wasDestroyed);
    }

    private void draw() {
        for (var obj : staticGameObjects) {
            obj.draw(gc);
        }

        for (var obj : gameObjects) {
            obj.draw(gc);
        }
    }
}
