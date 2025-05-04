package model.enums.plantable;

import java.util.List;

public enum MixedSeedSeasons {
    Spring(),
    Summer(),
    Fall(),
    Winter();

    private final List<Crops> crops;

    MixedSeedSeasons(List<Crops> crops) {
        this.crops = crops;
    }

    public List<Crops> getCrops() {
        return crops;
    }
}
