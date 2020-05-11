package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import src.base.Collider;
import src.base.Renderer;
import src.base.math.Vector2d;

public abstract class GameObject {
    protected Vector2d pos = new Vector2d();
    protected boolean wasDestroyed = false;
    protected Vector2d size = new Vector2d();
    protected Collider collider;
    protected Renderer renderer = new Renderer();

    public GameObject() {
        collider = new Collider(this);
    }

    public GameObject(boolean shouldCreateCollider) {
        if (shouldCreateCollider)
            collider = new Collider(this);
    }

    public GameObject(Vector2d pos) {
        this.pos = pos;
    }

    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    public Vector2d getSize() {
        return size;
    }

    public void setSize(Vector2d size) {
        this.size = size;
    }

    public boolean wasDestroyed() {
        return wasDestroyed;
    }

    public void destroy() {
        wasDestroyed = true;
    }

    public void onCollision(Collider another) {
    }

    public void start() {
    }

    abstract public void update(double dTime);

    abstract public void draw(GraphicsContext gc);
}
