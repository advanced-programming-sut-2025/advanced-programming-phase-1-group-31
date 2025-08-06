package io.github.some_example_name.model.materials.Tools;



import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.toolTypes.FishingPoleType;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

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
