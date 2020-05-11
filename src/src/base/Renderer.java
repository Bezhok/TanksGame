package src.base;

import javafx.scene.canvas.GraphicsContext;
import src.base.math.Vector2d;

public class Renderer {
    private Sprite sprite;

    private Vector2d pos = new Vector2d();

    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite.getImage(), pos.x - sprite.getSize().x / 2.0, pos.y - sprite.getSize().y / 2.0);
    }
}
