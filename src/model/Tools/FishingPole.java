package model.Tools;

import model.enums.toolTypes.FishingPoleType;

public class FishingPole implements Tool {
    private FishingPoleType fishingPoleType = FishingPoleType.Training;


    public void setFishingPoleType(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    public FishingPoleType getFishingPoleType() {
        return fishingPoleType;
    }

}
