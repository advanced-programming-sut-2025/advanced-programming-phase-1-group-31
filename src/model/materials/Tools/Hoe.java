package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;

public class Hoe implements Tool, Material {
    private AxePickHoeType hoeType = AxePickHoeType.Initial;

    public void setHoeType(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    @Override
    public void work() {

    }
}
