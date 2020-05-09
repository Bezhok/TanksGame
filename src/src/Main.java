package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import src.base.Collider;
import src.base.Levels;
import src.gameobject.Enemy;
import src.gameobject.GameObject;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    static public int width = 800;
    static public int height = 600;
    public static ArrayList<GameObject> gameObjectsBuffer = new ArrayList<>();
    private MediaPlayer backgroundPlayer;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
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


        levels.loadNext(inputHandler);
        clearScene();

        playBackgroundMusic();

        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(Math.abs(currentNanoTime - lastUpdate) / 1000000000.0);

                lastUpdate = currentNanoTime;
            }
        }.start();


        Thread.sleep(2000);
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


        stage.show();
        width = (int) stage.getWidth();
        height = (int) stage.getHeight();

        canvas.setWidth(width);
        canvas.setHeight(height);
    }

    private void update(double dTime) {
        gc.clearRect(0, 0, width, height);

        boolean isEnemyExists = false;
        for (var obj : gameObjects) {
            if (obj instanceof Enemy) isEnemyExists = true;
            obj.update(dTime);
        }

        if (!isEnemyExists) {
            clearScene();
            levels.loadNext(inputHandler);
        }

        for (var obj : gameObjects) {
            obj.draw(gc);
        }

        gameObjects.addAll(gameObjectsBuffer);
        gameObjectsBuffer.clear();

        gameObjects.removeIf(GameObject::wasDestroyed);
        Collider.getColliders().removeIf(collider -> collider.getGameObject().wasDestroyed());
    }
}
