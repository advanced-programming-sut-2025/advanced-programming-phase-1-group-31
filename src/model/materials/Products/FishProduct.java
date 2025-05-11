package model.materials.Products;

import model.enums.creature.FishTypes;
import model.materials.Material;
import model.materials.MaterialType;

public class FishProduct implements Material {
    private FishTypes fishTypes;

    public void setFishTypes(FishTypes fishTypes) {
        this.fishTypes = fishTypes;
    }

    @Override
    public MaterialType getType() {
        return fishTypes;
    }
}
