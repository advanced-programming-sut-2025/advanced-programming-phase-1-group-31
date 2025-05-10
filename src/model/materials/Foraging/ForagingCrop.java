package model.materials.Foraging;

import model.enums.foragings.ForagingCrops;
import model.materials.Material;
import model.materials.MaterialType;

public class ForagingCrop implements Material {
    private ForagingCrops foragingCrop;

    public ForagingCrops getForagingCrop() {
        return foragingCrop;
    }

    public void setForagingCrop(ForagingCrops foragingCrop) {
        this.foragingCrop = foragingCrop;
    }

    @Override
    public MaterialType getType() {
        return foragingCrop;
    }
}
