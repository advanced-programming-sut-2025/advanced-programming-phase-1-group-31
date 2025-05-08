package model;



import java.util.ArrayList;
// Includes all farms
public class Map{
    private ArrayList<Farm> farms = new ArrayList<>();
//    public static ArrayList<FruitTrees> allFruitTrees = new ArrayList<>();
//    public static ArrayList<Crop> allCrops = new ArrayList<>();
    private Tile[][] mainMap = new Tile[140][100];

    public Tile getMainMap(int x, int y) {
        return mainMap[x][y];
    }

    public  void setMainMap(Tile tile, int x, int y) {
        mainMap[x][y]= tile;
    }

    public  ArrayList<Farm> getFarms() {
        return farms;
    }

    public  void setFarms(ArrayList<Farm> farms) {
        this.farms = farms;
    }

    public  Tile[][] getMainMap() {
        return mainMap;
    }

    public void setMainMap(Tile[][] mainMap) {
        this.mainMap = mainMap;
    }
}
