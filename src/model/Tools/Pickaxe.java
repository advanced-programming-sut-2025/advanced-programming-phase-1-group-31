package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.AxePickHoeType;

public class Pickaxe implements Tool {
    private AxePickHoeType pickaxeType;

    public Pickaxe(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public void setPickaxeType(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public AxePickHoeType getPickaxeType() {
        return pickaxeType;
    }

    @Override
    public void work(Direction direction) {

    }
}
