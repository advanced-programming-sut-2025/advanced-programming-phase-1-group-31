package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;
import model.materials.MaterialType;

public class Hoe implements Tool, Material {
    private AxePickHoeType hoeType = AxePickHoeType.Initial;

    public void setHoeType(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
