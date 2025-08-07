package io.github.some_example_name.model.materials;



import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.model.Tile;
import io.github.some_example_name.model.enums.creature.CoopsAndBarnsTypes;
import io.github.some_example_name.model.enums.general.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Coop implements Material {
    private final CoopsAndBarnsTypes coopType;
    private Rectangle area;
    private RectangleMapObject out;
    private final ArrayList<Animal> animals;

    public Coop(CoopsAndBarnsTypes coopType) {
        this.coopType = coopType;
        animals = new ArrayList<>();
    }
    @Override
    public MaterialType getType() {
        return coopType;
    }

    @Override
    public String getTexturePath() {
        return coopType.getImagePath();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public boolean hasSpace() {
        return animals.size() < coopType.getCapacity();
    }

    public void addAnimal(Animal animal, Tile[][] map) {
        if (!hasSpace()) {
            throw new IllegalStateException("Building is at full capacity");
        }

//        for (int x = area.x; x < area.x + area.width; x++) {
//            for (int y = area.y; y < area.y + area.height; y++) {
//                Tile tile = map[x][y];
//                if (tile.getType() == TileType.COOP) {
//                    tile.setType(TileType.ANIMAL);
//                    tile.setMaterial(animal);
//                    animals.add(animal);
//                    animal.setLocation(new Point(x, y));
//                    return;
//                }
//            }
//        }

        throw new IllegalStateException("No free space inside the coop area");
    }
    public void removeAnimalByName(Animal animal, TiledMap tiledMap) {
        TiledMapTileLayer animalLayer = (TiledMapTileLayer)tiledMap.getLayers().get("animal");
        if (animalLayer != null) {
            animalLayer.setCell((int) animal.getLocation().x, (int) animal.getLocation().y, null);
        }
        animals.remove(animal);
//        Iterator<Animal> iterator = animals.iterator();
//        while (iterator.hasNext()) {
//            Animal animal = iterator.next();
//            if (animal.getName().equalsIgnoreCase(name)) {
//                Point p = animal.getLocation();
//                Tile tile = map[p.x][p.y];
//
//                if (area.contains(p)) {
//                    tile.setType(TileType.BARN);
//                    tile.setMaterial(this);
//                } else {
//                    tile.setType(TileType.EMPTY);
//                    tile.setMaterial(null);
//                }
//
//                iterator.remove();
//                return;
//            }
//        }
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

    public RectangleMapObject getOut() {
        return out;
    }

    public void setOut(RectangleMapObject out) {
        this.out = out;
    }

    @Override
    public String getName() {
        return coopType.getDisplayName();
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
