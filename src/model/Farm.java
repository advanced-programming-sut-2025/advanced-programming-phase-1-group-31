package model;



import java.awt.*;
import java.util.ArrayList;

import model.enums.general.TileType;
import model.materials.Barn;
import model.materials.Coop;

public class Farm {
   private Rectangle rectangle ;
    private Tile[][] mainMap ;


    public Farm(Point size) {
        this.rectangle = new Rectangle(0,0,size.x,size.y);
        int width = size.x;
        int height = size.y;
        this.mainMap = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.mainMap[i][j] = new Tile();
                mainMap[i][j].setType(TileType.EMPTY);
            }
        }
    }



    private GreenHouse greenhouse;

    private ArrayList<Coop> coops = new ArrayList<>();
    private ArrayList<Barn> barns = new ArrayList<>();
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

    public ArrayList<Coop> getCoops() {
        return coops;
    }

    public void setCoops(ArrayList<Coop> coops) {
        this.coops = coops;
    }

    public ArrayList<Barn> getBarns() {
        return barns;
    }

    public void setBarns(ArrayList<Barn> barns) {
        this.barns = barns;
    }
}
