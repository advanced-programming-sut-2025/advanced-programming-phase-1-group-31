package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.FishingPoleType;

public class FishingPole implements Tool {
    private FishingPoleType fishingPoleType;

    public FishingPole(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    public void setFishingPoleType(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    public FishingPoleType getFishingPoleType() {
        return fishingPoleType;
    }

    @Override
    public void work(Direction direction) {

    }
}
