package model.materials;

import model.enums.plantable.MixedSeedSeasons;

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
        return 0;
    }
}
