package io.github.some_example_name.model.enums.general;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.Vector;

public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);

    private final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Vector2 apply(Vector2 p) {
        return new Vector2(p.x + dx, p.y + dy);
    }

    public static Direction fromString(String s) {
        return switch (s.toLowerCase()) {
            case "up" -> UP;
            case "down" -> DOWN;
            case "left" -> LEFT;
            case "right" -> RIGHT;
            case "upleft" -> UP_LEFT;
            case "upright" -> UP_RIGHT;
            case "downleft" -> DOWN_LEFT;
            case "downright" -> DOWN_RIGHT;
            default -> null;
        };
    }

}
