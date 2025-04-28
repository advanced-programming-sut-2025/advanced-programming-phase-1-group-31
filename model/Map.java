package model;

import model.materials.Crop;
import model.materials.FruitTrees;

import java.util.ArrayList;
// Includes all farms
public class Map{
    public static ArrayList<Farm> farms = new ArrayList<>();
    public static ArrayList<FruitTrees> allFruitTrees = new ArrayList<>();
    public static ArrayList<Crop> allCrops = new ArrayList<>();
    public static Tile[][] mainMap = new Tile[50][50];

    public Tile getMainMap(int x, int y) {
        return mainMap[x][y];
    }

    public static void setMainMap(Tile tile, int x, int y) {
        Map.mainMap[x][y] = tile;
    }
}
