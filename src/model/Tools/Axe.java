package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.AxePickHoeType;

public class Axe implements Tool {
    private AxePickHoeType axeType;

    public Axe(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public void setAxeType(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public AxePickHoeType getAxeType() {
        return axeType;
    }

    @Override
    public void work(Direction direction) {


    }
}
