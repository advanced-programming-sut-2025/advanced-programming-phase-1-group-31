package common;

import java.awt.*;

public class Others {
    private final int number;
    private Point point;

    public Others(int number, Point point) {
        this.number = number;
        this.point = point;
    }

    public int getNumber() {
        return number;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Others [number=" + number + ", point=" + point + "]";
    }
}
