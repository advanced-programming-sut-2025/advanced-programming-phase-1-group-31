package model.materials;

import model.Tile;
import model.enums.creature.CoopsAndBarnsTypes;
import model.enums.general.TileType;

import java.awt.*;
import java.util.ArrayList;

public class Coop implements Material {
    private CoopsAndBarnsTypes coopType;
    private Rectangle area;
    private ArrayList<Animal> animals;

    public Coop(CoopsAndBarnsTypes coopType) {
        this.coopType = coopType;
        animals = new ArrayList<>();
    }
    @Override
    public MaterialType getType() {
        return coopType;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public boolean hasSpace() {
        return animals.size() < coopType.getCapacity();
    }

    public void addAnimal(Animal animal, Tile[][] map) {
        if (!hasSpace()) {
            throw new IllegalStateException("Building is at full capacity");
        }

        for (int x = area.x; x < area.x + area.width; x++) {
            for (int y = area.y; y < area.y + area.height; y++) {
                Tile tile = map[x][y];
                if (tile.getType() == TileType.COOP) {
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

    public CoopsAndBarnsTypes getCoopType() {
        return coopType;
    }

    public void setCoopType(CoopsAndBarnsTypes coopType) {
        this.coopType = coopType;
    }



    @Override
    public String getName() {
        return "Coop";
    }

}
