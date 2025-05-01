package model;

import java.awt.*;

import model.enums.TileType;

public class Tile {
   private TileType type;
   private Point point = new Point();


    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

}