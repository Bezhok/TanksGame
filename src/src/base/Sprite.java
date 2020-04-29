package src.base;

import javafx.scene.image.Image;

public class Sprite {
    private Image image;

    public Vector2i getSize() {
        return size;
    }

    private Vector2i size = new Vector2i();

    public Sprite(String name, int scale) {
        image = new Image("file:resources/" + name);
        var newSize = recalcSizes((int) image.getWidth(), (int) image.getHeight(), scale);
        image = new Image("file:resources/" + name, newSize.x, newSize.y, false, false);

        size = newSize;
    }

    public Sprite(String name) {
        image = new Image("file:resources/" + name);

        size.x = (int) image.getWidth();
        size.y = (int) image.getHeight();
    }

    public Sprite(String name, int width, int height) {
        image = new Image("file:resources/" + name, width, height, false, false);

        size.x = width;
        size.y = height;
    }

    public Image getImage() {
        return image;
    }

    private Vector2i recalcSizes(int imageWidth, int imageHeight, int scale) {
        var res = new Vector2i();
        if (imageHeight > imageWidth) {
            res.y = scale;
            res.x = (int) (imageWidth / (double) imageHeight * scale);
        } else {
            res.x = scale;
            res.y = (int) (imageHeight / (double) imageWidth * scale);
        }
        return res;
    }

    public void setSize(Vector2i size) {
        this.size = size;
    }
}
