package model.materials;

import model.Tile;
import model.enums.creature.CoopsAndBarnsTypes;
import model.enums.general.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Barn implements Material {
    private Rectangle area;
    private CoopsAndBarnsTypes barnType;
    private ArrayList<Animal> animals;


    public Barn(CoopsAndBarnsTypes barnType) {
        animals = new ArrayList<>();
        this.barnType = barnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }
    public boolean hasSpace() {
        return animals.size() < barnType.getCapacity();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimal(Animal animal, Tile[][] map) {
        if (!hasSpace()) {
            throw new IllegalStateException("Building is at full capacity");
        }

        for (int x = area.x; x < area.x + area.width; x++) {
            for (int y = area.y; y < area.y + area.height; y++) {
                Tile tile = map[x][y];
                if (tile.getType() == TileType.BARN) {
                    tile.setType(TileType.ANIMAL);
                    tile.setMaterial(animal);
                    animals.add(animal);
                    animal.setLocation(new Point(x, y));
                    return;
                }
            }
        }

        throw new IllegalStateException("No free space inside the coop area");
    }


    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }

    @Override
    public MaterialType getType() {
        return barnType;
    }

    @Override
    public String getName() {
        return barnType.getDisplayName();
    }
}