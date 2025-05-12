package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.FishingPoleType;

import java.util.Objects;

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

    @Override
    public String getName() {
        return "Fishing Pole " + fishingPoleType.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tool tool)) return false;
        return this.getClass().equals(tool.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
