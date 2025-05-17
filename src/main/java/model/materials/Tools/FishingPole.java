package model.materials.Tools;

import model.Result;
import model.enums.general.Direction;
import model.enums.toolTypes.FishingPoleType;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class FishingPole implements Tool, Material {
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
    public Result work(Direction direction) {
        return null;

    }

    @Override
    public MaterialType getType() {
        return fishingPoleType;
    }

    @Override
    public String getName() {
        return "Fishing Pole " + fishingPoleType.name();
    }

    @Override
    public int baseSellPrice() {
        return 0;
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