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
import src.base.Collider;
import src.base.Levels;
import src.gameobject.Enemy;
import src.gameobject.GameObject;
import src.gameobject.Player;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    static public int width;
    static public int height;
    public static ArrayList<GameObject> gameObjectsBuffer = new ArrayList<>();
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    double timer = 0;
    boolean isPaused = false;
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
        stage.setTitle("Game");
        root = new Group();
        theScene = new Scene(root);
        stage.setScene(theScene);

        launch(stage);

        levels = new Levels();
        inputHandler = new InputHandler(theScene);
        playBackgroundMusic();

        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(Math.abs(currentNanoTime - lastUpdate) / 1000000000.0);

                lastUpdate = currentNanoTime;
            }
        }.start();
    }

    private void clearScene() {
        for (var obj : gameObjects) {
            obj.destroy();
        }
        gameObjectsBuffer.clear();
        gameObjects.clear();
        Collider.getColliders().clear();
    }

    private void playBackgroundMusic() {
        String musicFile = "background.mp3";
        backgroundPlayer = new MediaPlayer(new Media(new File("resources/" + musicFile).toURI().toString()));
        backgroundPlayer.setVolume(0.1);
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
    }

    private void update(double dTime) {
        gc.clearRect(0, 0, width, height);

        boolean shouldStartNewLevel = false;

        if (!isPaused) {
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

        if (isPaused) {
            timer += dTime;
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.setFont(Font.font("Verdana", FontWeight.BOLD, 60));

            int levelIdx = levels.getCurrLevel() + 1;
            gc.fillText(
                    "level " + levelIdx,
                    width / 2,
                    height / 2
            );

        } else if (shouldStartNewLevel) {
            clearScene();
            levels.loadNext(inputHandler);
            isPaused = true;
        }

        if (timer >= 2) {
            isPaused = false;
        }

        if (!isPaused) {
            isPaused = false;
            timer = 0;
            for (var obj : gameObjects) {
                obj.draw(gc);
            }

            gameObjects.addAll(gameObjectsBuffer);
            gameObjectsBuffer.clear();

            Collider.processCollision();
            gameObjects.removeIf(GameObject::wasDestroyed);
        }
    }
}
