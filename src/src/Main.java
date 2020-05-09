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
import src.base.Vector2d;
import src.gameobject.Block;
import src.gameobject.Enemy;
import src.gameobject.GameObject;
import src.gameobject.Player;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    static public int width = 800;
    static public int height = 600;
    public static ArrayList<GameObject> gameObjectsBuffer = new ArrayList<>();

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private GraphicsContext gc;
    private InputHandler inputHandler;
    private Group root;
    private Scene theScene;

    public static void main(String[] args) {
        launch(args);
    }
    MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Game");
        root = new Group();
        theScene = new Scene(root);
        stage.setScene(theScene);

        launch(stage);


        double shiftY = 22;

        Block wall = new Block();
        wall.setPos(new Vector2d(width / 2.0, height * 2.0 / 3 + 100));
        wall.setSize(new Vector2d(width, height / 2.0));

        Block wall2 = new Block();
        wall2.setPos(new Vector2d(width / 2.0, height / 2));
        wall2.setSize(new Vector2d(20, height * 2.0 / 8.0));

        Block wall3 = new Block();
        wall3.setPos(new Vector2d(width / 2.0 + 150, height / 2));
        wall3.setSize(new Vector2d(20, height * 2.0 / 8.0));

        Block wall4 = new Block();
        wall4.setPos(new Vector2d(width - 100 / 2, height / 2 - 150));
        wall4.setSize(new Vector2d(100, 20));
        gameObjects.add(wall4);

        Block wall5 = new Block();
        wall5.setPos(new Vector2d(width - 100, height / 2 - 160));
        wall5.setSize(new Vector2d(20, 40));


        Player player = new Player();
        placeOn(player, wall, 100);

        Enemy enemy = new Enemy(player);
        placeOn(enemy, wall, 800);

        Enemy enemy2 = new Enemy(player);
        placeOn(enemy2, wall, 500);

        Enemy enemy3 = new Enemy(player);
        placeOn(enemy3, wall4, 1320);

        gameObjects.add(wall5);
        gameObjects.add(wall);
        gameObjects.add(wall2);
        gameObjects.add(player);
        gameObjects.add(enemy);
        gameObjects.add(enemy2);
        gameObjects.add(enemy3);

        inputHandler = new InputHandler(theScene);
        inputHandler.add(player);

        for (var obj : gameObjects) {
            obj.start();
        }

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

    private void placeOn(GameObject object, GameObject on, int x) {
        object.setPos(new Vector2d(x, on.getPos().y-on.getSize().y/2-object.getSize().y/2));
    }

    private void playBackgroundMusic() {
        String musicFile = "background.mp3";
        mediaPlayer = new MediaPlayer(new Media(new File("resources/" + musicFile).toURI().toString()));
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
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

        for (var obj : gameObjects) {
            obj.update(dTime);
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
