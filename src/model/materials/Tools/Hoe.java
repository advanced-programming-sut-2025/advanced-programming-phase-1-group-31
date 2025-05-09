package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;

public class Hoe implements Tool, Material {
    private AxePickHoeType hoeType = AxePickHoeType.Initial;

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
