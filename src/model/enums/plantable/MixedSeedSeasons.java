package model.enums.plantable;

import model.materials.MaterialType;

import java.util.List;

public enum MixedSeedSeasons implements MaterialType {
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
