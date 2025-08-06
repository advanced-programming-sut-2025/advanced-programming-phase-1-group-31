package io.github.some_example_name.model;

import java.awt.*;

public class GreenHouse {
    private Rectangle rectangle;
    private boolean hasBeenMade = false;

    public boolean isHasBeenMade() {
        return hasBeenMade;
    }

    public void setHasBeenMade(boolean hasBeenMade) {
        this.hasBeenMade = hasBeenMade;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

}
