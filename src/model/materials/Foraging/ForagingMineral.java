package model.materials.Foraging;

import model.enums.foragings.ForagingMinerals;
import model.materials.Material;
import model.materials.MaterialType;

public class ForagingMineral implements Material {
    private ForagingMinerals foragingMineral;

    public ForagingMineral(ForagingMinerals foragingMineral) {
        this.foragingMineral = foragingMineral;
    }

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

    @Override
    public String getName() {
        return foragingMineral.getDisplayName();
    }
}
