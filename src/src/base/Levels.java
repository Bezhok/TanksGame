package src.base;

import src.InputHandler;
import src.Main;
import src.gameobject.Block;
import src.gameobject.Enemy;
import src.gameobject.GameObject;
import src.gameobject.Player;

public class Levels {
    private int currLevel = -1;

    public int getCurrLevel() {
        return currLevel;
    }

    public void reset() {
        currLevel = -1;
    }

    private int levelsCount = 3;
    public void loadNext(InputHandler inputHandler) {
        inputHandler.clear();
        currLevel = (currLevel+1)%levelsCount;
        switch (currLevel) {
            case 0:
                level1(inputHandler);
                break;
            case 1:
                level2(inputHandler);
                break;
            case 2:
                level3(inputHandler);
                break;
        }
    }

    private void placeOn(GameObject object, GameObject on, int x) {
        object.setPos(new Vector2d(x, on.getPos().y-on.getSize().y/2-object.getSize().y/2));
    }

    private void level3(InputHandler inputHandler) {
        Block wall1 = new Block();
        wall1.setPos(new Vector2d(Main.width / 2.0, Main.height * 2.0 / 3 + 200));
        wall1.setSize(new Vector2d(Main.width, Main.height / 4.0));

        Player player = new Player(greenTank);
        placeOn(player, wall1, 100);
        Enemy enemy1 = new Enemy(yellowTank,player);
        placeOn(enemy1, wall1, 800);
        Enemy enemy2 = new Enemy(yellowTank,player);
        placeOn(enemy2, wall1, 500);
        Enemy enemy3 = new Enemy(yellowTank,player);
        placeOn(enemy3, wall1, 500);

        Main.gameObjectsBuffer.add(wall1);
        Main.gameObjectsBuffer.add(player);
        Main.gameObjectsBuffer.add(enemy1);
        Main.gameObjectsBuffer.add(enemy2);
        Main.gameObjectsBuffer.add(enemy3);

        inputHandler.add(player);

        for (var obj : Main.gameObjectsBuffer) {
            obj.start();
        }
    }

    String greenTank = "tank2.png";
    String yellowTank = "tank3.png";
    private void level1(InputHandler inputHandler) {
        Block wall1 = new Block();
        wall1.setPos(new Vector2d(Main.width / 2.0, Main.height * 2.0 / 3 + 200));
        wall1.setSize(new Vector2d(Main.width, Main.height / 4.0));

        Player player = new Player(greenTank);
        placeOn(player, wall1, 100);
        Enemy enemy1 = new Enemy(yellowTank, player);
        placeOn(enemy1, wall1, 800);
        Enemy enemy2 = new Enemy(yellowTank, player);
        placeOn(enemy2, wall1, 500);

        Main.gameObjectsBuffer.add(wall1);
        Main.gameObjectsBuffer.add(player);
        Main.gameObjectsBuffer.add(enemy1);
        Main.gameObjectsBuffer.add(enemy2);

        inputHandler.add(player);

        for (var obj : Main.gameObjectsBuffer) {
            obj.start();
        }
    }

    private void level2(InputHandler inputHandler) {
        Block wall1 = new Block();
        wall1.setPos(new Vector2d(Main.width / 2.0, Main.height * 2.0 / 3 + Main.height/3));
        wall1.setSize(new Vector2d(Main.width, Main.height / 4.0));

        Block wall2 = new Block();
        wall2.setSize(new Vector2d(20, Main.height / 8.0));
        placeOn(wall2, wall1, Main.width / 2);

        Block wall3 = new Block();
        wall3.setPos(new Vector2d(Main.width / 2.0 + Main.height/3, Main.height / 2));
        wall3.setSize(new Vector2d(20, Main.height * 2.0 / 8.0));

        Block wall4 = new Block();
        wall4.setPos(new Vector2d(Main.width - Main.height/16, Main.height / 2 ));
        wall4.setSize(new Vector2d(100, 20));
        Main.gameObjectsBuffer.add(wall4);

        Block wall5 = new Block();
        wall5.setPos(new Vector2d(Main.width - Main.height/8, Main.height / 2));
        wall5.setSize(new Vector2d(20, 40));


        Player player = new Player(greenTank);
        placeOn(player, wall1, 100);
        Enemy enemy1 = new Enemy(yellowTank,player);
        placeOn(enemy1, wall1, 800);
        Enemy enemy2 = new Enemy(yellowTank,player);
        placeOn(enemy2, wall1, 500);
        Enemy enemy3 = new Enemy(yellowTank,player);
        placeOn(enemy3, wall4, 1320);

        Main.gameObjectsBuffer.add(wall5);
        Main.gameObjectsBuffer.add(wall1);
        Main.gameObjectsBuffer.add(wall2);
        Main.gameObjectsBuffer.add(player);
        Main.gameObjectsBuffer.add(enemy1);
        Main.gameObjectsBuffer.add(enemy2);
        Main.gameObjectsBuffer.add(enemy3);
        
        inputHandler.add(player);

        for (var obj : Main.gameObjectsBuffer) {
            obj.start();
        }
    }
}
