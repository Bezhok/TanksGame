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

    // TODO bad realisation
    public static GraphicsContext gc;
    int width = 800;
    int height = 600;

    // TODO bad realisation
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<GameObject> gameObjectsTemp = new ArrayList<>();
    public static ArrayList<GameObject> temporyGameObjects = new ArrayList<>();
    InputHandler inputHandler;

    public static void main(String[] args) {
        launch(args);
    }
    Player player;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Canvas Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene(theScene);

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();




        player = new Player();
        player.setPos(new Vector2d(100, height / 2 + 35));
        player.gun.setGc(gc);

        Enemy enemy = new Enemy(player);
        enemy.setPos(new Vector2d(800, height / 2 + 35));
        enemy.gun.setGc(gc);

        Enemy enemy2 = new Enemy(player);
        enemy2.setPos(new Vector2d(500, height / 2 + 35));
        enemy2.gun.setGc(gc);

        Enemy enemy3 = new Enemy(player);
        enemy3.setPos(new Vector2d(780, height / 2  - 150 - 25));
        enemy3.gun.setGc(gc);
        gameObjects.add(enemy3);

        Block wall = new Block();
        wall.setPos(new Vector2d(width / 2.0, height * 2.0 / 3 + 100));
        wall.setSize(new Vector2d(width, height / 2.0));

        Block wall2 = new Block();
        wall2.setPos(new Vector2d(width / 2.0, height / 2));
        wall2.setSize(new Vector2d(20, height * 2.0 / 8.0));

        Block wall3 = new Block();
        wall3.setPos(new Vector2d(width / 2.0 + 150, height / 2));
        wall3.setSize(new Vector2d(20, height * 2.0 / 8.0));
//        gameObjects.add(wall3);

        Block wall4 = new Block();
        wall4.setPos(new Vector2d(width / 2.0 + 360, height / 2  - 150));
        wall4.setSize(new Vector2d(100, 20));
        gameObjects.add(wall4);

        Block wall5 = new Block();
        wall5.setPos(new Vector2d(width / 2.0 + 310, height / 2  - 160));
        wall5.setSize(new Vector2d(20, 40));
        gameObjects.add(wall5);

        gameObjects.add(wall);
        gameObjects.add(wall2);

        gameObjects.add(player);
        gameObjects.add(enemy);
        gameObjects.add(enemy2);


        inputHandler = new InputHandler(theScene);
        inputHandler.add(player);


        for (var obj : gameObjects) {
            obj.getRenderer().setGc(gc);
            obj.start();
        }

//
//
//       String musicFile = "expl.wav";     // For example
//
//        Media sound = new Media(
//        new File("resources/" + musicFile).toURI().toString());
//
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//
//            mediaPlayer.play();



        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(Math.abs(currentNanoTime - lastUpdate) / 1000000000.0);
                lastUpdate = currentNanoTime;
            }
        }.start();

        stage.show();

    }

    void update(double dTime) {
        gc.clearRect(0, 0, width, height);

        for (var obj: temporyGameObjects) {
            obj.update(dTime);
        }

        for (var obj : gameObjects) {
            obj.update(dTime);
        }

        for (var obj : gameObjects) {
            obj.draw();
        }

        for (var obj: temporyGameObjects) {
            obj.draw();
        }
        gc.fillText(Double.toString(player.getCurrPower()), 10, 10);

        gameObjects.addAll(gameObjectsTemp);
        gameObjectsTemp.clear();
        temporyGameObjects.removeIf(GameObject::wasDestroyed);
        gameObjects.removeIf(GameObject::wasDestroyed);
        Collider.getColliders().removeIf(collider -> collider.getGameObject().wasDestroyed());
    }
}
