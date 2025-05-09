package model.materials.Tools;

import model.enums.toolTypes.BackpackType;
import model.materials.Crop;
import model.materials.Fruit;
import model.materials.Material;

import java.util.ArrayList;

public class Backpack implements Tool, Material {
    public BackpackType backpackType = BackpackType.Initial;
    private ArrayList<Tool> tools = new ArrayList<>();
    private ArrayList<Crop> crops = new ArrayList<>();
    private ArrayList<Fruit> fruitTrees = new ArrayList<>();
    //wood


    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
