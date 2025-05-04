package model.enums;

import java.util.Arrays;
import java.util.List;

import static model.enums.Crops.*;

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
