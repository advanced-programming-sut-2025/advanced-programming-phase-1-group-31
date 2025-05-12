package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.AxePickHoeType;

public class Hoe implements Tool{
    private AxePickHoeType hoeType;

    public Hoe(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public void setHoeType(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public AxePickHoeType getHoeType() {
        return hoeType;
    }

    @Override
    public void work(Direction direction) {

    }
}
