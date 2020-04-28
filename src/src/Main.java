package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import src.base.Vector2d;
import src.gameobject.Block;
import src.gameobject.GameObject;
import src.gameobject.Player;

import java.util.ArrayList;

public class Main extends Application {

    GraphicsContext gc;
    int width = 800;
    int height = 600;
    ArrayList<GameObject> gameObjects = new ArrayList<>();

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

        Player player = new Player();
        Block wall = new Block();
        player.setPos(new Vector2d(200, 50));
        wall.setPos(new Vector2d(width / 2.0, height * 2.0 / 3 + 100));
        wall.setSize(new Vector2d(width, height / 2.0));
        gameObjects.add(player);
        gameObjects.add(wall);

        for (var obj : gameObjects) {
            obj.getRenderer().setGc(gc);
            obj.start();
        }


        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update(currentNanoTime);
            }
        }.start();

        stage.show();
    }

    void update(long currentNanoTime) {
        // Clear the canvas
        gc.clearRect(0, 0, width, height);

        for (var obj : gameObjects) {
            obj.update(currentNanoTime / 1000000000.0);
        }

        for (var obj : gameObjects) {
            obj.draw();
        }

    }
}
