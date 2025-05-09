package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;

public class Axe implements Tool, Material {
    private AxePickHoeType axeType = AxePickHoeType.Initial;

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
