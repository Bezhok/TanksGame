package src.base;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Renderer {
    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    private Vector2d pos = new Vector2d();

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }


    Sprite sprite;

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    GraphicsContext gc;


    public void draw() {
//        if (sprite != null)

        gc.drawImage(sprite.getImage(), pos.x-sprite.getSize().x/2.0, pos.y-sprite.getSize().y/2.0);
    }
}
