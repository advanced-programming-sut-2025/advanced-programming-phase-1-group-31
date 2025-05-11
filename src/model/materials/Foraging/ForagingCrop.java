package model.materials.Foraging;

import model.enums.foragings.ForagingCrops;
import model.materials.Material;

public class ForagingCrop implements Material {
    private ForagingCrops foragingCrop;

    public ForagingCrops getForagingCrop() {
        return foragingCrop;
    }

    public void setForagingCrop(ForagingCrops foragingCrop) {
        this.foragingCrop = foragingCrop;
    }


}
