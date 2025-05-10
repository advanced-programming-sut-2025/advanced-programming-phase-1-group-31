package model;

import java.awt.*;

import model.enums.general.TileType;
import model.materials.Material;

public class Tile {
   private TileType type;
   private Point point = new Point();
    private Material material;

    public TileType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
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