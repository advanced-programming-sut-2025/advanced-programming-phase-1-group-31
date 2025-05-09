package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;

public class Pickaxe implements Tool, Material {
    private AxePickHoeType pickaxeType = AxePickHoeType.Initial;


    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
