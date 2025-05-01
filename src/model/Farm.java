package model;

//import model.materials.Barn;
//import model.materials.Coop;
//import model.materials.Crop;
//import model.materials.FruitTrees;

import java.awt.*;
import java.util.ArrayList;

public class Farm {
   private Rectangle rectangle ;
    public Tile[][] mainMap = new Tile[55][35];


    public Farm() {

        this.rectangle = new Rectangle(0 , 0 , 55 , 35);
        for(int i=0; i<55 ; i++){
            for(int j=0; j<35; j++){
                mainMap[i][j] = new Tile();
                mainMap[i][j].setType(TileType.EMPTY);
            }
        }
    }

    private GreenHouse greenhouse;

//    private Coop coop;
//    private Barn barn;
    private Cottage cottage;
    private ArrayList<Quarry> quarryInFarm = new ArrayList<>();
    private ArrayList<Lake> lakeInFarm = new ArrayList<>();
//    private ArrayList<FruitTrees> fruitTreesInFarm = new ArrayList<>();
//    private ArrayList<Crop> cropsInFarm = new ArrayList<>();
//    private ArrayList<Rock> rocksInFarm = new ArrayList<>();
//    private ArrayList<Foraging> foragingsInFarm = new ArrayList<>();




    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Tile[][] getMainMap() {
        return mainMap;
    }

    public void setMainMap(Tile[][] mainMap) {
        this.mainMap = mainMap;
    }

    public GreenHouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(GreenHouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    public Cottage getCottage() {
        return cottage;
    }

    public void setCottage(Cottage cottage) {
        this.cottage = cottage;
    }

    public ArrayList<Quarry> getQuarryInFarm() {
        return quarryInFarm;
    }

    public void setQuarryInFarm(ArrayList<Quarry> quarryInFarm) {
        this.quarryInFarm = quarryInFarm;
    }

    public ArrayList<Lake> getLakeInFarm() {
        return lakeInFarm;
    }

    public void setLakeInFarm(ArrayList<Lake> lakeInFarm) {
        this.lakeInFarm = lakeInFarm;
    }
}
