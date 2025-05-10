package model;


import model.enums.general.TileType;
// Includes all farms
public class Map{
//    public static ArrayList<FruitTrees> allFruitTrees = new ArrayList<>();
//    public static ArrayList<Crop> allCrops = new ArrayList<>();
public Map() {
    int width = 140;
    int height = 100;
    this.mainMap = new Tile[width][height];
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            this.mainMap[i][j] = new Tile();
            mainMap[i][j].setType(TileType.EMPTY);
        }
    }
}
    private Tile[][] mainMap ;

    public Tile getMainMap(int x, int y) {
        return mainMap[x][y];
    }

    public  void setMainMap(Tile tile, int x, int y) {
        mainMap[x][y]= tile;
    }

    public  Tile[][] getMainMap() {
        return mainMap;
    }

    public void setMainMap(Tile[][] mainMap) {
        this.mainMap = mainMap;
    }
}
