package model.materials.Tools;

import model.enums.toolTypes.BackpackType;
import model.materials.Crop;
import model.materials.Fruit;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.ArrayList;

public class Backpack implements Tool, Material {
    public BackpackType backpackType = BackpackType.Initial;



    public void setBackpackType(BackpackType backpackType) {
        this.backpackType = backpackType;
    }

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
