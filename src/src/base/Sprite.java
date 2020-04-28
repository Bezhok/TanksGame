package src.base;

import javafx.scene.image.Image;

public class Sprite {
    private Image image;

    public Sprite(String name, int scale) {
        image = new Image("file:resources/" + name);
        var newSize = recalcSizes((int) image.getWidth(), (int) image.getHeight(), scale);
        image = new Image("file:resources/" + name, newSize.x, newSize.y, false, false);
    }

    public Sprite(String name) {
        image = new Image("file:resources/" + name);
    }

    public Sprite(String name, int width, int height) {
        image = new Image("file:resources/" + name);
        image = new Image("file:resources/" + name, width, height, false, false);
    }

    public Image getImage() {
        return image;
    }

    public Vector2i recalcSizes(int imageWidth, int imageHeight, int scale) {
        var res = new Vector2i();
        if (imageHeight > imageWidth) {
            res.y = scale;
            res.x = imageWidth / imageHeight * scale;
        } else {
            res.x = scale;
            res.y = imageHeight / imageWidth * scale;
        }
        return res;
    }
}
