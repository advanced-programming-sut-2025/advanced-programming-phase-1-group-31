package io.github.some_example_name.model;

import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.materials.Material;

import java.awt.*;



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
