package model.materials.Tools;

import model.enums.toolTypes.FishingPoleType;
import model.materials.Material;

public class FishingPole implements Tool, Material {
    private FishingPoleType fishingPoleType = FishingPoleType.Training;


    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
