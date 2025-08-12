package io.github.some_example_name.model.materials;



import io.github.some_example_name.model.Tile;
import io.github.some_example_name.model.enums.creature.CoopsAndBarnsTypes;
import io.github.some_example_name.model.enums.general.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Barn implements Material {
    private Rectangle area;
    private final CoopsAndBarnsTypes barnType;
    private final ArrayList<Animal> animals;


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
    public Boolean hasSpace() {
        return animals.size() < barnType.getCapacity();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
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
    public void removeAnimalByName(String name, Tile[][] map) {
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getName().equalsIgnoreCase(name)) {
                Point p = animal.getLocation();
                Tile tile = map[p.x][p.y];

                if (area.contains(p)) {
                    tile.setType(TileType.BARN);
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

    @Override
    public int baseSellPrice() {
        return 0;
    }
}
