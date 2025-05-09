package model;

import model.materials.Barn;
import model.materials.Coop;
import model.materials.Crop;
import model.materials.Fruit;

import java.util.ArrayList;

public class Farm {
    private GreenHouse greenhouse;
    private Coop coop;
    private Barn barn;
    private Cottage cottage;
    private ArrayList<Quarry> quarryInFarm = new ArrayList<>();
    private ArrayList<Lake> lakeInFarm = new ArrayList<>();
    private ArrayList<Fruit> fruitTreesInFarm = new ArrayList<>();
    private ArrayList<Crop> cropsInFarm = new ArrayList<>();
    private ArrayList<Rock> rocksInFarm = new ArrayList<>();
}
