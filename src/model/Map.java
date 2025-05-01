package model;



import java.util.ArrayList;
// Includes all farms
public class Map{
    public static ArrayList<Farm> farms = new ArrayList<>();
//    public static ArrayList<FruitTrees> allFruitTrees = new ArrayList<>();
//    public static ArrayList<Crop> allCrops = new ArrayList<>();
    public static Tile[][] mainMap = new Tile[90][140];

    public Tile getMainMap(int x, int y) {
        return mainMap[x][y];
    }

    public static void setMainMap(Tile tile, int x, int y) {
        Map.mainMap[x][y] = tile;
    }

    public static ArrayList<Farm> getFarms() {
        return farms;
    }

    public static void setFarms(ArrayList<Farm> farms) {
        Map.farms = farms;
    }

    public static Tile[][] getMainMap() {
        return mainMap;
    }

    public static void setMainMap(Tile[][] mainMap) {
        Map.mainMap = mainMap;
    }
}
