package model.materials.Foraging;

import model.enums.foragings.ForagingMinerals;
import model.materials.Material;
import model.materials.Tools.MaterialType;

public class ForagingMineral implements Material {
    private ForagingMinerals foragingMineral;

    @Override
    public MaterialType getType() {
        return foragingMineral;
    }
}
