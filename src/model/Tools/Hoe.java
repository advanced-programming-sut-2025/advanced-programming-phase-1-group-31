package model.Tools;

import model.enums.toolTypes.AxePickHoeType;

public class Hoe implements Tool{
    private AxePickHoeType hoeType = AxePickHoeType.Initial;

    public void setHoeType(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public AxePickHoeType getHoeType() {
        return hoeType;
    }

}
