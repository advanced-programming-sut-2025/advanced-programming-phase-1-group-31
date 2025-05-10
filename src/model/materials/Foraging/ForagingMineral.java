package model.materials.Foraging;

import model.enums.foragings.ForagingMinerals;
import model.materials.Material;
import model.materials.MaterialType;

public class ForagingMineral implements Material {
    private ForagingMinerals foragingMineral;

    public ForagingMinerals getForagingMineral() {
        return foragingMineral;
    }

    public void setForagingMineral(ForagingMinerals foragingMineral) {
        this.foragingMineral = foragingMineral;
    }

    @Override
    public MaterialType getType() {
        return foragingMineral;
    }
}
