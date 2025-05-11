package model.materials.Tools;

import model.enums.toolTypes.BackpackType;
import model.materials.Material;

public class Backpack implements Tool, Material {
    public BackpackType backpackType = BackpackType.Initial;



    public void setBackpackType(BackpackType backpackType) {
        this.backpackType = backpackType;
    }


    @Override
    public void work() {

    }
}
