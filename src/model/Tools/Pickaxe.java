package model.Tools;

import model.enums.toolTypes.AxePickHoeType;

public class Pickaxe implements Tool {
    private AxePickHoeType pickaxeType = AxePickHoeType.Initial;


    public void setPickaxeType(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public AxePickHoeType getPickaxeType() {
        return pickaxeType;
    }

}
