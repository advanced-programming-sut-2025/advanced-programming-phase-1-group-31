package model.materials.Foraging;

import model.enums.foragings.ForagingSeeds;
import model.materials.Material;
import model.materials.Tools.MaterialType;

public class ForagingSeed implements Material {
    private ForagingSeeds foragingSeed;

    @Override
    public MaterialType getType() {
        return foragingSeed;
    }
}
