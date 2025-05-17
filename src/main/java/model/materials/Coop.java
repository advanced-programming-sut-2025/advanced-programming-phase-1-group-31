package model.materials;

import model.Tile;
import model.enums.creature.CoopsAndBarnsTypes;
import model.enums.general.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

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
    public void removeAnimalByName(String name, Tile[][] map) {
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getName().equalsIgnoreCase(name)) {
                Point p = animal.getLocation();
                Tile tile = map[p.x][p.y];

                if (area.contains(p)) {
                    tile.setType(TileType.COOP);
                    tile.setMaterial(this);
                } else {
                    tile.setType(TileType.EMPTY);
                    tile.setMaterial(null);
                }

                iterator.remove();
                return;
            }
        }
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

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }

}
