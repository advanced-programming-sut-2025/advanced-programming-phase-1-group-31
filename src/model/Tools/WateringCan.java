package model.Tools;

import model.enums.toolTypes.WateringCanType;

public class WateringCan implements Tool {
    private WateringCanType wateringCanType = WateringCanType.Initial;


    public WateringCanType getWateringCanType() {
        return wateringCanType;
    }

    public void setWateringCanType(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }

    public void work() {

    }

}
