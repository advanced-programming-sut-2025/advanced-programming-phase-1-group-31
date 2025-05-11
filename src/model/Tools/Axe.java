package model.Tools;

import model.enums.toolTypes.AxePickHoeType;

public class Axe implements Tool {
    private AxePickHoeType axeType = AxePickHoeType.Initial;

    public void setAxeType(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public AxePickHoeType getAxeType() {
        return axeType;
    }

}
