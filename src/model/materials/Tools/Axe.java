package model.materials.Tools;

import model.enums.toolTypes.AxePickHoeType;
import model.materials.Material;

public class Axe implements Tool, Material {
    private AxePickHoeType axeType = AxePickHoeType.Initial;

    public void setAxeType(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    @Override
    public void work() {

    }
}
