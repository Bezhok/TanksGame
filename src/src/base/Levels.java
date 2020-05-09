package src.base;

import src.InputHandler;
import src.Main;
import src.gameobject.Block;
import src.gameobject.Enemy;
import src.gameobject.GameObject;
import src.gameobject.Player;

public class Levels {
    public void loadNext(InputHandler inputHandler) {
        inputHandler.clear();
        level1(inputHandler);
    }

    private void placeOn(GameObject object, GameObject on, int x) {
        object.setPos(new Vector2d(x, on.getPos().y-on.getSize().y/2-object.getSize().y/2));
    }

    private void level1(InputHandler inputHandler) {
        Block wall = new Block();
        wall.setPos(new Vector2d(Main.width / 2.0, Main.height * 2.0 / 3 + 200));
        wall.setSize(new Vector2d(Main.width, Main.height / 4.0));

        Block wall2 = new Block();
        wall2.setSize(new Vector2d(20, Main.height / 8.0));
        placeOn(wall2, wall, Main.width / 2);

        Block wall3 = new Block();
        wall3.setPos(new Vector2d(Main.width / 2.0 + 150, Main.height / 2));
        wall3.setSize(new Vector2d(20, Main.height * 2.0 / 8.0));

        Block wall4 = new Block();
        wall4.setPos(new Vector2d(Main.width - 100 / 2, Main.height / 2 - 150));
        wall4.setSize(new Vector2d(100, 20));
        Main.gameObjectsBuffer.add(wall4);

        Block wall5 = new Block();
        wall5.setPos(new Vector2d(Main.width - 100, Main.height / 2 - 160));
        wall5.setSize(new Vector2d(20, 40));


        Player player = new Player();
        placeOn(player, wall, 100);

        Enemy enemy = new Enemy(player);
        placeOn(enemy, wall, 800);

        Enemy enemy2 = new Enemy(player);
        placeOn(enemy2, wall, 500);

        Enemy enemy3 = new Enemy(player);
        placeOn(enemy3, wall4, 1320);

        Main.gameObjectsBuffer.add(wall5);
        Main.gameObjectsBuffer.add(wall);
        Main.gameObjectsBuffer.add(wall2);
        Main.gameObjectsBuffer.add(player);
        Main.gameObjectsBuffer.add(enemy);
        Main.gameObjectsBuffer.add(enemy2);
        Main.gameObjectsBuffer.add(enemy3);
        
        inputHandler.add(player);

        for (var obj : Main.gameObjectsBuffer) {
            obj.start();
        }

    }
}
