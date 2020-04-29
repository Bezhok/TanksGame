package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import src.base.Collider;
import src.base.Vector2d;
import src.gameobject.Block;
import src.gameobject.Enemy;
import src.gameobject.GameObject;
import src.gameobject.Player;

import java.util.ArrayList;

public class Main extends Application {

    // TODO bad realisation
    public static GraphicsContext gc;
    int width = 800;
    int height = 600;

    // TODO bad realisation
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<GameObject> temporyGameObjects = new ArrayList<>();
    InputHandler inputHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Canvas Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene(theScene);

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        Enemy enemy = new Enemy();
        enemy.setPos(new Vector2d(600, height / 2 + 35));
        enemy.gun.setGc(gc);

        Player player = new Player();
        player.setPos(new Vector2d(500, height / 2 + 35));
        player.gun.setGc(gc);

        Block wall = new Block();
        wall.setPos(new Vector2d(width / 2.0, height * 2.0 / 3 + 100));
        wall.setSize(new Vector2d(width, height / 2.0));

        Block wall2 = new Block();
        wall2.setPos(new Vector2d(width / 2.0, height / 2));
        wall2.setSize(new Vector2d(20, height * 2.0 / 4.0));

        gameObjects.add(player);
        gameObjects.add(enemy);
        gameObjects.add(wall);
        gameObjects.add(wall2);

        inputHandler = new InputHandler(theScene);
        inputHandler.add(player);


        for (var obj : gameObjects) {
            obj.getRenderer().setGc(gc);
            obj.start();
        }

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

        temporyGameObjects.removeIf(GameObject::wasDestroyed);
        gameObjects.removeIf(GameObject::wasDestroyed);
        Collider.getColliders().removeIf(collider -> collider.getGameObject().wasDestroyed());
    }
}
