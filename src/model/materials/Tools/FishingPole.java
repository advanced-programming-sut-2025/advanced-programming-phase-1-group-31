package model.materials.Tools;

import model.enums.toolTypes.FishingPoleType;
import model.materials.Material;
import model.materials.MaterialType;

public class FishingPole implements Tool, Material {
    private FishingPoleType fishingPoleType = FishingPoleType.Training;


    public void setFishingPoleType(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}
