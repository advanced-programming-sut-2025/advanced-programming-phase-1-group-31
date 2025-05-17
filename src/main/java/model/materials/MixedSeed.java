package model.materials;

import model.enums.plantable.MixedSeedSeasons;

import java.util.Objects;

public class MixedSeed implements Material{

    private MixedSeedSeasons mixedSeed;


    public MixedSeed(MixedSeedSeasons mixedSeed) {
        this.mixedSeed = mixedSeed;
    }

    public void setMixedSeed(MixedSeedSeasons mixedSeed) {
        this.mixedSeed = mixedSeed;
    }

    @Override
    public MaterialType getType() {
        return mixedSeed;
    }

    @Override
    public String getName() {
        return mixedSeed.name();
    }

    @Override
    public int baseSellPrice() {
        return mixedSeed.getBaseSellPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }
}
