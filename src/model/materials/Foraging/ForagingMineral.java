package model.materials.Foraging;

import model.enums.foragings.ForagingMinerals;
import model.materials.Material;

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

}
