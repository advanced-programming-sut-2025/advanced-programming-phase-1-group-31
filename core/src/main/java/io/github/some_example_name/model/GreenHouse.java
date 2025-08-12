package io.github.some_example_name.model;

import java.awt.*;

public class GreenHouse {
    private Rectangle rectangle;
    private Boolean hasBeenMade = false;

    public Boolean isHasBeenMade() {
        return hasBeenMade;
    }

    public void setHasBeenMade(Boolean hasBeenMade) {
        this.hasBeenMade = hasBeenMade;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

}
