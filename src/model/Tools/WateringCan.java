package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.WateringCanType;

public class WateringCan implements Tool {
    private WateringCanType wateringCanType;

    public WateringCan(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }


    public WateringCanType getWateringCanType() {
        return wateringCanType;
    }

    public void setWateringCanType(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }

    @Override
    public void work(Direction direction) {

    }
}
