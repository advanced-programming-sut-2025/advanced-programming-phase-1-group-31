package model.materials.Tools;

import model.materials.Crop;
import model.materials.FruitTrees;
import model.enums.BackpackType;
import model.materials.Material;

import java.util.ArrayList;

public class Backpack implements Tool, Material {
    public BackpackType backpackType = BackpackType.Initial;
    private ArrayList<Tool> tools = new ArrayList<>();
    private ArrayList<Crop> crops = new ArrayList<>();
    private ArrayList<FruitTrees> fruitTrees = new ArrayList<>();
    //wood

    public void work() {

    }
}
