package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    GraphicsContext gc;
    int width = 800;
    int height = 600;

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

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.fillText("Hello, World!", 60, 50);
        gc.strokeText("Hello, World!", 60, 50);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update(currentNanoTime);
            }
        }.start();

//        Image earth = new Image( "earth.png" );
//        gc.drawImage( earth, 180, 100 );

        stage.show();
    }

    void update(long currentNanoTime) {
        // Clear the canvas
        gc.clearRect(0, 0, width, height);


    }
}
