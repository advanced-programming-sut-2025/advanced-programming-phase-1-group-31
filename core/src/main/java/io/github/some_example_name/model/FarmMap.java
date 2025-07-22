package io.github.some_example_name.model;


import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.some_example_name.model.materials.Material;

import java.awt.*;
import java.util.ArrayList;

// Includes all farms
public class FarmMap {
//    public static ArrayList<FruitTrees> allFruitTrees = new ArrayList<>();
//    public static ArrayList<Crop> allCrops = new ArrayList<>();
public FarmMap() {
    mainMaps = new ArrayList<>();
//    int width = 140;
//    int height = 100;
//    this.mainMap = new Tile[width][height];
//    for (int i = 0; i < width; i++) {
//        for (int j = 0; j < height; j++) {
//            this.mainMap[i][j] = new Tile();
//            mainMap[i][j].setType(TileType.EMPTY);
//        }
//    }
}
    private ArrayList<Tile> mainMaps;
    private TiledMap tmxMap;

    public Tile getTileByPoint(int x, int y) {
        return mainMaps.stream().filter(t -> t.getPoint().getX() == x && t.getPoint().getY() == y).findFirst().orElse(null);
    }

    public  void addTile(Tile tile) {
        mainMaps.add(tile);
    }
    public void createAndAddTile(Point point , Material material ) {
        Tile tile = new Tile();
        tile.setPoint(point);
        tile.setMaterial(material);
        addTile(tile);

    }

    public ArrayList<Tile> getMainMaps() {
        return mainMaps;
    }

    public void setMainMaps(ArrayList<Tile> mainMaps) {
        this.mainMaps = mainMaps;
    }

    public TiledMap getTmxMap() {
        return tmxMap;
    }

    public void setTmxMap(TiledMap tmxMap) {
        this.tmxMap = tmxMap;
    }

    //delete
    private Tile[][] mainMap ;

    public Tile[][] getMainMap() {
        return mainMap;
    }

    public void setMainMap(Tile[][] mainMap) {
        this.mainMap = mainMap;
    }
}
