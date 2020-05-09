package src.gameobject;

import javafx.scene.canvas.GraphicsContext;
import src.base.Collider;
import src.base.Renderer;
import src.base.Vector2d;

public abstract class GameObject {
    protected Vector2d pos = new Vector2d();

    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    public Vector2d getSize() {
        return size;
    }

    public GameObject() {
        collider =  new Collider(this);
    }

    public GameObject(boolean shouldCreateCollider) {
        if (shouldCreateCollider)
            collider =  new Collider(this);
    }

    public boolean wasDestroyed() {
        return wasDestroyed;
    }

    void destroy() {
        wasDestroyed = true;
    }

    protected boolean wasDestroyed = false;
    public void onCollision(Collider another) {}

    public GameObject(Vector2d pos) {
        this.pos = pos;
    }

    public void setSize(Vector2d size) {
        this.size = size;
    }

    Vector2d size = new Vector2d();
    Collider collider ;

    Renderer renderer = new Renderer();

    public void start() {}
    abstract public void update(double dTime);
    abstract public void draw(GraphicsContext gc);
}
