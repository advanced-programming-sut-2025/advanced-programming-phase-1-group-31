package model.materials.Tools;

import model.enums.toolTypes.WateringCanType;
import model.materials.Material;

public class WateringCan implements Tool, Material {
    private WateringCanType wateringCanType = WateringCanType.Initial;


    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
