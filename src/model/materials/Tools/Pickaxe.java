package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;

public class Pickaxe implements Tool, Material {
    private AxePickHoeType pickaxeType = AxePickHoeType.Initial;


    public void setPickaxeType(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }


    @Override
    public void work() {

    }
}
